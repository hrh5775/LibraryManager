package at.team2.server.remote;

import at.team2.application.facade.DvdApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.interfaces.DvdRemoteObjectInf;
import at.team2.domain.entities.Dvd;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

@Stateful
@Remote(at.team2.common.interfaces.DvdRemoteObjectInf.class)
public class DvdRemoteObject extends UnicastRemoteObject implements DvdRemoteObjectInf {
    private static Type typeSmall = new TypeToken<List<DvdSmallDto>>() {}.getType();
    private static Type typeCreatorPersonSmall = new TypeToken<List<CreatorPersonSmallDto>>() {}.getType();
    private DvdApplicationFacade _dvdFacade;

    public DvdRemoteObject() throws RemoteException {
        super(0);
    }

    private DvdApplicationFacade getDvdFacade() {
        if(_dvdFacade == null) {
            _dvdFacade = new DvdApplicationFacade();
        }

        return _dvdFacade;
    }

    @Override
    public DvdSmallDto getDvdSmallById(int id) throws RemoteException {
        DvdApplicationFacade facade = getDvdFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, DvdSmallDto.class);
        }

        return null;
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList() throws RemoteException {
        DvdApplicationFacade facade = getDvdFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getList(), typeSmall);
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList(String searchString) throws RemoteException {
        DvdApplicationFacade facade = getDvdFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.search(searchString), typeSmall);
    }

    @Override
    public DvdDetailedDto getDvdDetailedById(int id) throws RemoteException {
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

    @Override
    protected void finalize() throws Throwable {
        if(_dvdFacade != null) {
            _dvdFacade.closeDbSession();
        }

        super.finalize();
    }
}