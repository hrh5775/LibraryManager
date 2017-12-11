package at.team2.server.remote.rmi;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.rmi.AccountRemoteObjectInf;
import at.team2.server.common.AccountBase;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountRemoteObject extends UnicastRemoteObject implements AccountRemoteObjectInf {
    private AccountBase _accountBase;

    public AccountRemoteObject() throws RemoteException {
        _accountBase = new AccountBase();
    }

    @Override
    public AccountDetailedDto login(String userName, String password) throws RemoteException {
        return _accountBase.doLogin(userName, password);
    }

    @Override
    public void logout(AccountDetailedDto account) throws RemoteException {
        _accountBase.doLogout(account);
    }

    @Override
    protected void finalize() throws Throwable {
        _accountBase.close();
        super.finalize();
    }
}