package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.interfaces.ejb.DvdRemote;
import at.team2.server.common.DvdBase;

import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local
@Remote(DvdRemote.class)
public class Dvd extends DvdBase implements DvdRemote {
    @Override
    public DvdSmallDto getDvdSmallById(int id) {
        return doGetDvdSmallById(id);
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList() {
        return doGetDvdSmallList();
    }

    @Override
    public List<DvdSmallDto> getDvdSmallList(String searchString) {
        return doGetDvdSmallList(searchString);
    }

    @Override
    public DvdDetailedDto getDvdDetailedById(int id) {
        return doGetDvdDetailedById(id);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}