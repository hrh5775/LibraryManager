package at.team2.common.interfaces.rmi;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BookRemoteObjectInf extends Remote {
    public BookSmallDto getBookSmallById(int id) throws RemoteException;
    public List<BookSmallDto> getBookSmallList() throws RemoteException;
    public List<BookSmallDto> getBookSmallList(String searchString) throws RemoteException;

    public BookDetailedDto getBookDetailedById(int id) throws RemoteException;
}