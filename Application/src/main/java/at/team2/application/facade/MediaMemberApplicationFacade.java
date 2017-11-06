package at.team2.application.facade;

import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.small.MediaMemberDto;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.MediaMemberFacade;
import at.team2.domain.entities.MediaMember;
import at.team2.domain.enums.properties.MediaMemberProperty;
import javafx.util.Pair;

public class MediaMemberApplicationFacade extends BaseApplicationFacade<MediaMember,MediaMemberDto,MediaMemberProperty> {
    private static MediaMemberApplicationFacade _instance;
    private MediaMemberFacade _facade = new MediaMemberFacade();

    private MediaMemberApplicationFacade() {
    }

    public static MediaMemberApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new MediaMemberApplicationFacade();
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
    public Pair<Integer, List<Pair<MediaMemberProperty, String>>> add(MediaMemberDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        MediaMember entity = mapper.map(value, MediaMember.class);
        List<Pair<MediaMemberProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT),list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Integer, List<Pair<MediaMemberProperty, String>>> update(MediaMemberDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        MediaMember entity = mapper.map(value,MediaMember.class);
        List<Pair<MediaMemberProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT),list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Boolean, List<Pair<MediaMemberProperty, String>>> delete(int id) {
       List<Pair<MediaMemberProperty, String>> list = _facade.getById(id).validate();

       if(list.size() == 0) {
           return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
       }

       return new Pair<>(false, new LinkedList<>());
    }
}