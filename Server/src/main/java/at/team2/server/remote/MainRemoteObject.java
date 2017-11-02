package at.team2.server.remote;

import at.team2.connector.configuration.ConnectionInfo;
import at.team2.connector.interfaces.BookRemoteObjectInf;
import at.team2.connector.interfaces.MainRemoteObjectInf;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainRemoteObject extends UnicastRemoteObject implements MainRemoteObjectInf {
    private BookRemoteObjectInf _bookRemoteObjectInf;

    public MainRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public int getVersion() {
        return ConnectionInfo.version;
    }

    @Override
    public BookRemoteObjectInf getBookRemoteObject() throws RemoteException {
        if(_bookRemoteObjectInf == null) {
            _bookRemoteObjectInf = new BookRemoteObject();
        }

        return _bookRemoteObjectInf;
    }
}