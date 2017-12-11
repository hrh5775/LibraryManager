package at.team2.common.interfaces.rmi;

import at.team2.common.dto.detailed.AccountDetailedDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountRemoteObjectInf extends Remote {
    public AccountDetailedDto login(String userName, String password) throws RemoteException;
    public void logout(AccountDetailedDto account) throws RemoteException;
}