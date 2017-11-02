package at.team2.server.remote;

import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.interfaces.BookRemoteObjectInf;
import at.team2.common.interfaces.MainRemoteObjectInf;
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