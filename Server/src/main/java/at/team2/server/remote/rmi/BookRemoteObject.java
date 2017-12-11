package at.team2.server.remote.rmi;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.interfaces.rmi.BookRemoteObjectInf;
import at.team2.server.common.BookBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BookRemoteObject extends UnicastRemoteObject implements BookRemoteObjectInf {
    private BookBase _bookBase;

    public BookRemoteObject() throws RemoteException {
        _bookBase = new BookBase();
    }

    @Override
    public BookSmallDto getBookSmallById(int id) throws RemoteException {
        return _bookBase.doGetBookSmallById(id);
    }

    @Override
    public List<BookSmallDto> getBookSmallList() throws RemoteException {
        return _bookBase.doGetBookSmallList();
    }

    @Override
    public List<BookSmallDto> getBookSmallList(String searchString) throws RemoteException {
        return _bookBase.doGetBookSmallList(searchString);
    }

    @Override
    public BookDetailedDto getBookDetailedById(int id) throws RemoteException {
        return _bookBase.doGetBookDetailedById(id);
    }

    @Override
    protected void finalize() throws Throwable {
        _bookBase.close();
        super.finalize();
    }
}