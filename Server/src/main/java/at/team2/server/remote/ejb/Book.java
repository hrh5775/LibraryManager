package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.interfaces.ejb.BookRemote;
import at.team2.server.common.BookBase;

import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.Serializable;
import java.util.List;

@Stateless
@WebService
@Remote(BookRemote.class)
public class Book extends BookBase implements BookRemote, Serializable {
    @WebMethod
    @Override
    public BookSmallDto getBookSmallById(int id) {
        return doGetBookSmallById(id);
    }

    @WebMethod
    @Override
    public List<BookSmallDto> getBookSmallList() {
        return doGetBookSmallList();
    }

    @WebMethod
    public List<BookSmallDto> getBookSmallListBySearch(String searchString) {
        return getBookSmallList(searchString);
    }

    @WebMethod(exclude = true)
    @Override
    public List<BookSmallDto> getBookSmallList(String searchString) {
        return doGetBookSmallList(searchString);
    }

    @WebMethod
    @Override
    public BookDetailedDto getBookDetailedById(int id) {
        return doGetBookDetailedById(id);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}