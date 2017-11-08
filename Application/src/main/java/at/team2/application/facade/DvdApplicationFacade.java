package at.team2.application.facade;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
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

public class DvdApplicationFacade extends BaseApplicationFacade<Dvd, DvdDetailedDto, DvdProperty> {
    private static DvdApplicationFacade _instance;
    private DvdFacade _facade = new DvdFacade();

    public static DvdApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new DvdApplicationFacade();
        }

        return _instance;
    }

    private DvdApplicationFacade() {
    }

    public List<Dvd> search(String searchString){
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

        return _facade.filter(connector);
    }

    @Override
    public Dvd getById(int id) {
        return _facade.getById(id);
    }

    @Override
    public List<Dvd> getList() {
        return _facade.getList();
    }

    @Override
    public void closeSession() {
        _facade.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<DvdProperty, String>>> add(DvdDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = mapper.map(value, Dvd.class);
        List<Pair<DvdProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Integer, List<Pair<DvdProperty, String>>> update(DvdDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = mapper.map(value, Dvd.class);
        List<Pair<DvdProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Boolean, List<Pair<DvdProperty, String>>> delete(int id) {
        List<Pair<DvdProperty, String>> list = _facade.getById(id).validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(false, new LinkedList<>());
    }
}