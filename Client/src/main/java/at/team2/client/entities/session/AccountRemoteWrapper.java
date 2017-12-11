package at.team2.client.entities.session;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.ejb.AccountRemote;
import at.team2.common.interfaces.rmi.AccountRemoteObjectInf;
import java.rmi.RemoteException;

public class AccountRemoteWrapper implements AccountRemoteObjectInf {
    private AccountRemote _accountRemote;

    public AccountRemoteWrapper(AccountRemote accountRemote) {
        _accountRemote = accountRemote;
    }

    @Override
    public AccountDetailedDto login(String userName, String password) throws RemoteException {
        return _accountRemote.login(userName, password);
    }

    @Override
    public void logout(AccountDetailedDto account) throws RemoteException {
        _accountRemote.logout(account);
    }
}