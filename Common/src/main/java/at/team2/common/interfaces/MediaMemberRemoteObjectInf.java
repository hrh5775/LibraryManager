package at.team2.common.interfaces;

import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.MediaMemberSmallDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MediaMemberRemoteObjectInf extends Remote {
    public MediaMemberSmallDto getMediaMemberSmallById(int id) throws RemoteException;
    public List<MediaMemberSmallDto> getMediaMemberSmallList() throws RemoteException;
    public MediaMemberSmallDto getMediaMemberByIndex(String searchString) throws RemoteException;
    public MediaMemberDetailedDto getMediaMemberDetailedById(int id) throws RemoteException;
    public List<MediaMemberSmallDto> getMediaMemberSmallListByMediaId(int id) throws RemoteException;
}