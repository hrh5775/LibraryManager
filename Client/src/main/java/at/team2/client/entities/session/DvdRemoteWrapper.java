package at.team2.client.entities.session;

import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.interfaces.ejb.DvdRemote;
import at.team2.common.interfaces.rmi.DvdRemoteObjectInf;

import java.rmi.RemoteException;
import java.util.List;

public class DvdRemoteWrapper implements DvdRemoteObjectInf {
    private DvdRemote _dvdRemote;

    public DvdRemoteWrapper(DvdRemote dvdRemote) {
        _dvdRemote = dvdRemote;
    }

    @Override
    public DvdSmallDto getDvdSmallById(int id) throws RemoteException {
        return _dvdRemote.getDvdSmallById(id);
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList() throws RemoteException {
        return _dvdRemote.getDvdSmallList();
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList(String searchString) throws RemoteException {
        return _dvdRemote.getDvdSmallList(searchString);
    }

    @Override
    public DvdDetailedDto getDvdDetailedById(int id) throws RemoteException {
        return _dvdRemote.getDvdDetailedById(id);
    }
}