package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.ejb.MediaMemberRemote;
import at.team2.server.common.MediaMemberBase;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.List;

@Stateless
@Local
@Remote(MediaMemberRemote.class)
public class MediaMember extends MediaMemberBase implements MediaMemberRemote, Serializable {
    @Override
    public MediaMemberSmallDto getMediaMemberSmallById(int id) {
        return doGetMediaMemberSmallById(id);
    }

    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallList() {
        return doGetMediaMemberSmallList();
    }

    @Override
    public MediaMemberSmallDto getMediaMemberByIndex(String searchString) {
        return doGetMediaMemberByIndex(searchString);
    }

    @Override
    public MediaMemberDetailedDto getMediaMemberDetailedById(int id) {
        return doGetMediaMemberDetailedById(id);
    }

    @Override
    public List<MediaMemberSmallDto> getMediaMemberSmallListByMediaId(int id) {
        return doGetMediaMemberSmallListByMediaId(id);
    }
}