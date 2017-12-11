package at.team2.client.entities.session;

import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.ejb.MediaMemberRemote;
import at.team2.common.interfaces.rmi.MediaMemberRemoteObjectInf;

import java.rmi.RemoteException;
import java.util.List;

public class MediaMemberRemoteWrapper implements MediaMemberRemoteObjectInf {
    private MediaMemberRemote _mediaMemberRemote;

    public MediaMemberRemoteWrapper(MediaMemberRemote mediaMemberRemote) {
        _mediaMemberRemote = mediaMemberRemote;
    }

    @Override
    public MediaMemberSmallDto getMediaMemberSmallById(int id) throws RemoteException {
        return _mediaMemberRemote.getMediaMemberSmallById(id);
    }

    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallList() throws RemoteException {
        return _mediaMemberRemote.getMediaMemberSmallList();
    }

    @Override
    public MediaMemberSmallDto getMediaMemberByIndex(String searchString) throws RemoteException {
        return _mediaMemberRemote.getMediaMemberByIndex(searchString);
    }

    @Override
    public MediaMemberDetailedDto getMediaMemberDetailedById(int id) throws RemoteException {
        return _mediaMemberRemote.getMediaMemberDetailedById(id);
    }

    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallListByMediaId(int id) throws RemoteException {
        return _mediaMemberRemote.getMediaMemberSmallListByMediaId(id);
    }
}