package at.team2.common.interfaces.rmi;

import at.team2.common.dto.detailed.AccountDetailedDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageRemoteObjectInf extends Remote {
    public boolean sendMessageForInterLibraryLoan(String message, AccountDetailedDto updater) throws RemoteException;
    public String receiveMessageForInterLibraryLoan(AccountDetailedDto updater) throws RemoteException;
}