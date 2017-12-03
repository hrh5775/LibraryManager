package at.team2.server.remote;

import at.team2.application.facade.AccountApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.AccountRemoteObjectInf;
import at.team2.domain.entities.Account;
import org.modelmapper.ModelMapper;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Stateless
@Remote(AccountRemoteObjectInf.class)
public class AccountRemoteObject extends UnicastRemoteObject implements AccountRemoteObjectInf {
    private AccountApplicationFacade _accountFacade;

    public AccountRemoteObject() throws RemoteException {
        super(0);
    }

    private AccountApplicationFacade getAccountFacade() {
        if(_accountFacade == null) {
            _accountFacade = new AccountApplicationFacade();
        }

        return _accountFacade;
    }

    public AccountDetailedDto login(String userName, String password) throws RemoteException {
        System.out.println("in login");
        AccountApplicationFacade facade = getAccountFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = facade.login(userName, password);

        if(entity != null) {
            return mapper.map(entity, AccountDetailedDto.class);
        }

        return null;
    }

    @Override
    public void logout(AccountDetailedDto account) throws RemoteException {
        AccountApplicationFacade facade = getAccountFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = mapper.map(account, Account.class);
        facade.logout(entity.getId());
    }

    @Override
    protected void finalize() throws Throwable {
        if(_accountFacade != null) {
            _accountFacade.closeDbSession();
        }

        super.finalize();
    }
}