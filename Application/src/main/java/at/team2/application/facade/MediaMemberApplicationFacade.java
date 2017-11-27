package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.RoleHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.MatchType;
import org.modelmapper.ModelMapper;
import java.util.LinkedList;
import java.util.List;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.MediaMemberFacade;
import at.team2.domain.entities.MediaMember;
import at.team2.domain.enums.properties.MediaMemberProperty;
import javafx.util.Pair;

public class MediaMemberApplicationFacade extends BaseApplicationFacade<MediaMember, MediaMemberSmallDto, AccountDetailedDto, MediaMemberProperty> {
    private MediaMemberFacade _mediaMemberFacade;

    public MediaMemberApplicationFacade() {
        super();
    }

    private MediaMemberFacade getMediaMemberFacade() {
        if(_mediaMemberFacade == null) {
            _mediaMemberFacade = new MediaMemberFacade(getSession());
        }

        return _mediaMemberFacade;
    }

    @Override
    public MediaMember getById(int id) {
        return getMediaMemberFacade().getById(id);
    }

    @Override
    public List<MediaMember> getList() {
        return getMediaMemberFacade().getList();
    }

    @Override
    public void closeSession() {
        if(_mediaMemberFacade != null) {
            _mediaMemberFacade.closeSession();
            _mediaMemberFacade = null;
        }

        super.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<MediaMemberProperty, String>>> add(MediaMemberSmallDto value, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            ModelMapper mapper = MapperHelper.getMapper();
            MediaMember entity = mapper.map(value, MediaMember.class);
            List<Pair<MediaMemberProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getMediaMemberFacade().add(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<MediaMemberProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(MediaMemberProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Integer, List<Pair<MediaMemberProperty, String>>> update(MediaMemberSmallDto value, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            ModelMapper mapper = MapperHelper.getMapper();
            MediaMember entity = mapper.map(value, MediaMember.class);
            List<Pair<MediaMemberProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getMediaMemberFacade().update(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<MediaMemberProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(MediaMemberProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Boolean, List<Pair<MediaMemberProperty, String>>> delete(int id, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            MediaMemberFacade mediaMemberFacade = getMediaMemberFacade();
            List<Pair<MediaMemberProperty, String>> list = mediaMemberFacade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(mediaMemberFacade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<MediaMemberProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(MediaMemberProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public MediaMember getMediaMemberByIndex(String index) {
        FilterConnector<MediaMemberProperty, MediaMemberProperty> connector = new FilterConnector<>(
                new Filter<>(index, MediaMemberProperty.FULL_INDEX, MatchType.EQUALS, CaseType.IGNORE_CASE));

        List<MediaMember> list = getMediaMemberFacade().filter(connector);

        if(list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    public List<MediaMember> getListByMediaId(int id) {
        FilterConnector<MediaMemberProperty, MediaMemberProperty> connector = new FilterConnector<>(
                new Filter<>(id, MediaMemberProperty.MEDIA__ID, MatchType.EQUALS, CaseType.IGNORE_CASE));

        return getMediaMemberFacade().filter(connector);
    }
}