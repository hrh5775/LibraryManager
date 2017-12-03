package at.team2.server.remote;

import at.team2.common.interfaces.MessageRemoteObjectInf;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Stateless
@Remote(MessageRemoteObjectInf.class)
public class MessageRemoteObject extends UnicastRemoteObject implements MessageRemoteObjectInf {
    public MessageRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public boolean sendMessageForInterLibraryLoan(String message) throws RemoteException {
        // @todo:
        return false;
    }

    @Override
    public String receiveMessageForInterLibraryLoan() throws RemoteException {
        // @todo:
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}