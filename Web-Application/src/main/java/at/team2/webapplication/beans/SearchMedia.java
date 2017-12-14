package at.team2.webapplication.beans;

import at.team2.common.dto.detailed.MediaDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.interfaces.ejb.BookRemote;
import at.team2.common.interfaces.ejb.DvdRemote;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SearchMedia implements Serializable {
    @EJB
    private DvdRemote _dvdRemote;
    @EJB
    private BookRemote _bookRemote;
    private List<MediaSmallDto> _mediaList;
    private String _searchValue;
    private boolean _showBooks;
    private boolean _showDvds;
    private MediaDetailedDto _mediaDetailed;

    public SearchMedia() {
        _mediaList = new LinkedList<>();
        _searchValue = "";
    }

    public List<MediaSmallDto> getMediaList() {
        return _mediaList;
    }

    public String getSearchValue() {
        return _searchValue;
    }

    public void setSearchValue(String searchValue) {
        _searchValue = searchValue;
    }

    public boolean getShowBooks() {
        return _showBooks;
    }

    public void setShowBooks(boolean showBooks) {
        _showBooks = showBooks;
    }

    public boolean getShowDvds() {
        return _showDvds;
    }

    public void setShowDvds(boolean showDvds) {
        _showDvds = showDvds;
    }

    public MediaDetailedDto getMediaDetailed() {
        return _mediaDetailed;
    }

    public boolean getIsSearchButtonDisabled() {
        return _searchValue == null || _searchValue.length() == 0;
    }

    public void search() {
        _mediaList.clear();

        if(!getIsSearchButtonDisabled()) {
            if(getShowBooks() || (!getShowDvds() && !getShowBooks())) {
                _mediaList.addAll(_bookRemote.getBookSmallList(getSearchValue()));
            }

            if(getShowDvds() || (!getShowDvds() && !getShowBooks())) {
                _mediaList.addAll(_dvdRemote.getDvdSmallList(getSearchValue()));
            }
        }
    }

    public void showDetails(MediaSmallDto media) {
        if(media instanceof BookSmallDto) {
            _mediaDetailed = _bookRemote.getBookDetailedById(((BookSmallDto) media).getId());
        } else if(media instanceof DvdSmallDto) {
            _mediaDetailed = _dvdRemote.getDvdDetailedById(((DvdSmallDto) media).getId());
        }
    }
}