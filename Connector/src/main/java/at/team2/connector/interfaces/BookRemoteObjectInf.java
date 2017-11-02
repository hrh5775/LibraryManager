package at.team2.connector.interfaces;

import at.team2.connector.dto.small.BookSmallDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BookRemoteObjectInf extends Remote {
    public BookSmallDto getBookSmallById(int id) throws RemoteException;
    public List<BookSmallDto> getBookSmallList() throws RemoteException;
}
