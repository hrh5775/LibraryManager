package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.ejb.MediaMemberRemote;
import at.team2.server.common.MediaMemberBase;

import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.Serializable;
import java.util.List;

@Stateless
@WebService
@Remote(MediaMemberRemote.class)
public class MediaMember extends MediaMemberBase implements MediaMemberRemote, Serializable {
    @WebMethod
    @Override
    public MediaMemberSmallDto getMediaMemberSmallById(int id) {
        return doGetMediaMemberSmallById(id);
    }

    @WebMethod
    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallList() {
        return doGetMediaMemberSmallList();
    }

    @WebMethod
    @Override
    public MediaMemberSmallDto getMediaMemberByIndex(String searchString) {
        return doGetMediaMemberByIndex(searchString);
    }

    @WebMethod
    @Override
    public MediaMemberDetailedDto getMediaMemberDetailedById(int id) {
        return doGetMediaMemberDetailedById(id);
    }

    @WebMethod
    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallListByMediaId(int id) {
        return doGetMediaMemberSmallListByMediaId(id);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}