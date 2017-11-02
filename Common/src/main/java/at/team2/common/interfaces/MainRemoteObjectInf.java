package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainRemoteObjectInf extends Remote {
    public int getVersion() throws RemoteException;
    public BookRemoteObjectInf getBookRemoteObject() throws RemoteException;
}
