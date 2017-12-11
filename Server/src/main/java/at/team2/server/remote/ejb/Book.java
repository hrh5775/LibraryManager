package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.interfaces.ejb.BookRemote;
import at.team2.server.common.BookBase;

import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.List;

@Stateless
@Local
@Remote(BookRemote.class)
public class Book extends BookBase implements BookRemote, Serializable {
    @Override
    public BookSmallDto getBookSmallById(int id) {
        return doGetBookSmallById(id);
    }

    @Override
    public List<BookSmallDto> getBookSmallList() {
        return doGetBookSmallList();
    }

    @Override
    public List<BookSmallDto> getBookSmallList(String searchString) {
        return doGetBookSmallList(searchString);
    }

    @Override
    public BookDetailedDto getBookDetailedById(int id) {
        return doGetBookDetailedById(id);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}