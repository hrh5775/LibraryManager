package at.team2.client.entities.session;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.interfaces.ejb.BookRemote;
import at.team2.common.interfaces.rmi.BookRemoteObjectInf;

import java.rmi.RemoteException;
import java.util.List;

public class BookRemoteWrapper implements BookRemoteObjectInf {
    private BookRemote _bookRemote;

    public BookRemoteWrapper(BookRemote bookRemote) {
        _bookRemote = bookRemote;
    }

    @Override
    public BookSmallDto getBookSmallById(int id) throws RemoteException {
        return _bookRemote.getBookSmallById(id);
    }

    @Override
    public List<BookSmallDto> getBookSmallList() throws RemoteException {
        return _bookRemote.getBookSmallList();
    }

    @Override
    public List<BookSmallDto> getBookSmallList(String searchString) throws RemoteException {
        return _bookRemote.getBookSmallList(searchString);
    }

    @Override
    public BookDetailedDto getBookDetailedById(int id) throws RemoteException {
        return _bookRemote.getBookDetailedById(id);
    }
}