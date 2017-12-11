package at.team2.server.remote.rmi;

import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.rmi.MediaMemberRemoteObjectInf;
import at.team2.server.common.MediaMemberBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MediaMemberRemoteObject extends UnicastRemoteObject implements MediaMemberRemoteObjectInf {
    private MediaMemberBase _mediaMemberBase;

    public MediaMemberRemoteObject() throws RemoteException {
        _mediaMemberBase = new MediaMemberBase();
    }

    @Override
    public MediaMemberSmallDto getMediaMemberSmallById(int id) throws RemoteException {
        return _mediaMemberBase.doGetMediaMemberSmallById(id);
    }

    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallList() throws RemoteException {
        return _mediaMemberBase.doGetMediaMemberSmallList();
    }

    @Override
    public MediaMemberSmallDto getMediaMemberByIndex(String searchString) throws RemoteException {
        return _mediaMemberBase.doGetMediaMemberByIndex(searchString);
    }

    @Override
    public MediaMemberDetailedDto getMediaMemberDetailedById(int id) throws RemoteException {
        return _mediaMemberBase.doGetMediaMemberDetailedById(id);
    }

    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallListByMediaId(int id) throws RemoteException {
        return _mediaMemberBase.doGetMediaMemberSmallListByMediaId(id);
    }

    @Override
    protected void finalize() throws Throwable {
        _mediaMemberBase.close();
        super.finalize();
    }
}