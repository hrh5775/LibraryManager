package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

@javax.ejb.Remote
public interface MessageRemoteObjectInf extends Remote {
    public boolean sendMessageForInterLibraryLoan(String message) throws RemoteException;
    public String receiveMessageForInterLibraryLoan() throws RemoteException;
}