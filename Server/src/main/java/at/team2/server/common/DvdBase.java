package at.team2.server.common;

import at.team2.application.facade.DvdApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.domain.entities.Dvd;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DvdBase {
    private static Type typeSmall = new TypeToken<List<DvdSmallDto>>() {}.getType();
    private static Type typeCreatorPersonSmall = new TypeToken<List<CreatorPersonSmallDto>>() {}.getType();
    private DvdApplicationFacade _dvdFacade;

    private DvdApplicationFacade getDvdFacade() {
        if(_dvdFacade == null) {
            _dvdFacade = new DvdApplicationFacade();
        }

        return _dvdFacade;
    }

    public DvdSmallDto doGetDvdSmallById(int id) {
        DvdApplicationFacade facade = getDvdFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, DvdSmallDto.class);
        }

        return null;
    }

    public List<DvdSmallDto> doGetDvdSmallList() {
        DvdApplicationFacade facade = getDvdFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getList(), typeSmall);
    }

    public List<DvdSmallDto> doGetDvdSmallList(String searchString) {
        DvdApplicationFacade facade = getDvdFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.search(searchString), typeSmall);
    }

    public DvdDetailedDto doGetDvdDetailedById(int id) {
        DvdApplicationFacade facade = getDvdFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = facade.getById(id);

        if(entity != null) {
            DvdDetailedDto result = mapper.map(entity, DvdDetailedDto.class);
            result.setCreatorPersons(mapper.map(entity.getMedia().getCreatorPersons(), typeCreatorPersonSmall));
            return result;
        }

        return null;
    }

    public void close() {
        if(_dvdFacade != null) {
            _dvdFacade.closeDbSession();
        }
    }
}