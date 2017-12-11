package at.team2.common.interfaces.ejb;

import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.MediaMemberSmallDto;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface MediaMemberRemote {
    public MediaMemberSmallDto getMediaMemberSmallById(int id);
    public List<MediaMemberSmallDto> getMediaMemberSmallList();
    public MediaMemberSmallDto getMediaMemberByIndex(String searchString);
    public MediaMemberDetailedDto getMediaMemberDetailedById(int id);
    public List<MediaMemberSmallDto> getMediaMemberSmallListByMediaId(int id);
}