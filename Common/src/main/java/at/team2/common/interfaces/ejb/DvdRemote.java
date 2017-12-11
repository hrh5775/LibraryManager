package at.team2.common.interfaces.ejb;

import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.DvdSmallDto;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface DvdRemote {
    public DvdSmallDto getDvdSmallById(int id);
    public List<DvdSmallDto> getDvdSmallList();
    public List<DvdSmallDto> getDvdSmallList(String searchString);

    public DvdDetailedDto getDvdDetailedById(int id);
}