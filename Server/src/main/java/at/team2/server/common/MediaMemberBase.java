package at.team2.server.common;

import at.team2.application.facade.MediaMemberApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.domain.entities.MediaMember;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MediaMemberBase {
    private static Type typeSmall = new TypeToken<List<MediaMemberSmallDto>>() {}.getType();
    private MediaMemberApplicationFacade _mediaMemberFacade;

    private MediaMemberApplicationFacade getMediamemberFacade() {
        if(_mediaMemberFacade == null) {
            _mediaMemberFacade = new MediaMemberApplicationFacade();
        }

        return _mediaMemberFacade;
    }

    public MediaMemberSmallDto doGetMediaMemberSmallById(int id) {
        MediaMemberApplicationFacade facade = getMediamemberFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        MediaMember entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, MediaMemberSmallDto.class);
        }

        return null;
    }

    public List<MediaMemberSmallDto> doGetMediaMemberSmallList() {
        MediaMemberApplicationFacade facade = getMediamemberFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getList(), typeSmall);
    }

    public MediaMemberSmallDto doGetMediaMemberByIndex(String searchString) {
        MediaMemberApplicationFacade facade = getMediamemberFacade();
        MediaMember entity = facade.getMediaMemberByIndex(searchString);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, MediaMemberSmallDto.class);
        }

        return null;
    }

    public MediaMemberDetailedDto doGetMediaMemberDetailedById(int id) {
        MediaMemberApplicationFacade facade = getMediamemberFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        MediaMember entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, MediaMemberDetailedDto.class);
        }

        return null;
    }

    public List<MediaMemberSmallDto> doGetMediaMemberSmallListByMediaId(int id) {
        MediaMemberApplicationFacade facade = getMediamemberFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getListByMediaId(id), typeSmall);
    }

    public void close() {
        if(_mediaMemberFacade != null) {
            _mediaMemberFacade.closeDbSession();
        }
    }
}