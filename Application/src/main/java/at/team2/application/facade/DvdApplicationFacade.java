package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.MapperHelper;
import at.team2.application.helper.RoleHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.DvdFacade;
import at.team2.domain.entities.Dvd;
import at.team2.domain.enums.properties.DvdProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

public class DvdApplicationFacade extends BaseApplicationFacade<Dvd, DvdDetailedDto, AccountDetailedDto, DvdProperty> {
    private DvdFacade _dvdFacade;

    public DvdApplicationFacade() {
        super();
    }

    private DvdFacade getDvdFacade() {
        if(_dvdFacade == null) {
            _dvdFacade = new DvdFacade(getSession());
        }

        return _dvdFacade;
    }

    @Override
    public Dvd getById(int id) {
        return getDvdFacade().getById(id);
    }

    @Override
    public List<Dvd> getList() {
        return getDvdFacade().getList();
    }

    @Override
    public void closeSession() {
        if(_dvdFacade != null) {
            _dvdFacade.closeSession();
            _dvdFacade = null;
        }

        super.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<DvdProperty, String>>> add(DvdDetailedDto value, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Dvd entity = mapper.map(value, Dvd.class);
            List<Pair<DvdProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getDvdFacade().add(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<DvdProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(DvdProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Integer, List<Pair<DvdProperty, String>>> update(DvdDetailedDto value, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Dvd entity = mapper.map(value, Dvd.class);
            List<Pair<DvdProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getDvdFacade().update(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<DvdProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(DvdProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Boolean, List<Pair<DvdProperty, String>>> delete(int id, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            DvdFacade dvdFacade = getDvdFacade();
            List<Pair<DvdProperty, String>> list = dvdFacade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(dvdFacade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<DvdProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(DvdProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public List<Dvd> search(String searchString){
        /*String[] items = searchString.split(" ");
        FilterConnector<DvdProperty, DvdProperty> connector = null;
        FilterConnector<DvdProperty, DvdProperty> tmpConnector;
        FilterConnector<DvdProperty, DvdProperty> lastConnector = null;

        for(int i = 0; i < items.length; i++) {
            if(connector == null) {
                connector = getFilterConnector(items[i]);
                lastConnector = connector;
            } else {
                tmpConnector =  getFilterConnector(items[i]);
                lastConnector.setRightFilterConnector(ConnectorType.AND, tmpConnector);
                lastConnector = tmpConnector;
            }
        }*/

        return getDvdFacade().filter(getFilterConnector(searchString));
    }

    private FilterConnector<DvdProperty, DvdProperty> getFilterConnector(String searchString) {
        FilterConnector<DvdProperty, DvdProperty> connector = new FilterConnector<>(
                new FilterConnector<>(
                        new FilterConnector<>(
                                new Filter<>(searchString, DvdProperty.MEDIA__STANDARD_MEDIA_ID, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, DvdProperty.MEDIA__TITLE, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        ),
                        ConnectorType.OR,
                        new FilterConnector<>(
                                new Filter<>(searchString, DvdProperty.MEDIA__DESCRIPTION, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, DvdProperty.MEDIA__PUBLISHER, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        )
                ),
                ConnectorType.OR,
                new FilterConnector<>(
                        new FilterConnector<>(
                                new Filter<>(searchString, DvdProperty.MEDIA__PUBLISHER_TYPE, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, DvdProperty.MEDIA__CREATOR_PERSON, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        ),
                        ConnectorType.OR,
                        new FilterConnector<>(
                                new Filter<>(searchString, DvdProperty.MEDIA__CREATOR_TYPE, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, DvdProperty.MEDIA__GENRE, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        )
                )
        );

        return connector;
    }
}