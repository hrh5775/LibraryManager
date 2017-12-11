package at.team2.common.interfaces.rmi;

import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.DvdSmallDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DvdRemoteObjectInf extends Remote {
    public DvdSmallDto getDvdSmallById(int id) throws RemoteException;
    public List<DvdSmallDto> getDvdSmallList() throws RemoteException;
    public List<DvdSmallDto> getDvdSmallList(String searchString) throws RemoteException;

    public DvdDetailedDto getDvdDetailedById(int id) throws RemoteException;
}