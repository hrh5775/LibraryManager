package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.RoleHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
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
    private static MediaMemberApplicationFacade _instance;
    private MediaMemberFacade _facade;

    private MediaMemberApplicationFacade() {
    }

    public static MediaMemberApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new MediaMemberApplicationFacade();
            _instance._facade = new MediaMemberFacade();
        }

        return _instance;
    }

    @Override
    public MediaMember getById(int id) {
        return _facade.getById(id);
    }

    @Override
    public List<MediaMember> getList() {
        return _facade.getList();
    }

    @Override
    public void closeSession() {
        _facade.closeSession();
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
                return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT), list);
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
                return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT), list);
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
            List<Pair<MediaMemberProperty, String>> list = _facade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
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

        List<MediaMember> list = _facade.filter(connector);

        if(list.size() > 0) {
            return list.get(0);
        }

        return null;
    }
}