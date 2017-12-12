package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.interfaces.ejb.DvdRemote;
import at.team2.server.common.DvdBase;

import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Stateless
@WebService
@Remote(DvdRemote.class)
public class Dvd extends DvdBase implements DvdRemote {
    @WebMethod
    @Override
    public DvdSmallDto getDvdSmallById(int id) {
        return doGetDvdSmallById(id);
    }

    @WebMethod
    @Override
    public List<DvdSmallDto> getDvdSmallList() {
        return doGetDvdSmallList();
    }

    @WebMethod
    public List<DvdSmallDto> getDvdSmallListBySearch(String searchString) {
        return getDvdSmallList(searchString);
    }

    @WebMethod(exclude = true)
    @Override
    public List<DvdSmallDto> getDvdSmallList(String searchString) {
        return doGetDvdSmallList(searchString);
    }

    @WebMethod
    @Override
    public DvdDetailedDto getDvdDetailedById(int id) {
        return doGetDvdDetailedById(id);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}