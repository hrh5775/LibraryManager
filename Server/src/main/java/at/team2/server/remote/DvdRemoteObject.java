package at.team2.server.remote;

import at.team2.application.facade.DvdApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.interfaces.DvdRemoteObjectInf;
import at.team2.domain.entities.Dvd;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DvdRemoteObject extends UnicastRemoteObject implements DvdRemoteObjectInf {
    private static Type typeSmall = new TypeToken<List<DvdSmallDto>>() {}.getType();

    public DvdRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public DvdSmallDto getDvdSmallById(int id) throws RemoteException {
        DvdApplicationFacade facade = DvdApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, DvdSmallDto.class);
        }

        return null;
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList() {
        DvdApplicationFacade facade = DvdApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getList(), typeSmall);
    }

    public List<DvdSmallDto> getDvdSmallList(String searchString){
        DvdApplicationFacade facade =  DvdApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.search(searchString), typeSmall);
    }

    @Override
    public DvdDetailedDto getDvdDetailedById(int id) throws RemoteException {
        DvdApplicationFacade facade = DvdApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, DvdDetailedDto.class);
        }

        return null;
    }
}