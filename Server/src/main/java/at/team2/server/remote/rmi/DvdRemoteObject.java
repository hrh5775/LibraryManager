package at.team2.server.remote.rmi;

import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.interfaces.rmi.DvdRemoteObjectInf;
import at.team2.server.common.DvdBase;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DvdRemoteObject extends UnicastRemoteObject implements DvdRemoteObjectInf {
    DvdBase _dvdBase;

    public DvdRemoteObject() throws RemoteException {
        _dvdBase = new DvdBase();
    }

    @Override
    public DvdSmallDto getDvdSmallById(int id) throws RemoteException {
        return _dvdBase.doGetDvdSmallById(id);
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList() throws RemoteException {
        return _dvdBase.doGetDvdSmallList();
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList(String searchString) throws RemoteException {
        return _dvdBase.doGetDvdSmallList(searchString);
    }

    @Override
    public DvdDetailedDto getDvdDetailedById(int id) throws RemoteException {
        return _dvdBase.doGetDvdDetailedById(id);
    }

    @Override
    protected void finalize() throws Throwable {
        _dvdBase.close();
        super.finalize();
    }
}