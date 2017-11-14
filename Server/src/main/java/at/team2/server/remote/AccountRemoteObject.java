package at.team2.server.remote;

import at.team2.application.facade.AccountApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.AccountRemoteObjectInf;
import at.team2.domain.entities.Account;
import org.modelmapper.ModelMapper;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountRemoteObject extends UnicastRemoteObject implements AccountRemoteObjectInf {
    protected AccountRemoteObject() throws RemoteException {
        super(0);
    }

    public AccountDetailedDto login(String userName, String password) throws RemoteException {
        AccountApplicationFacade facade = AccountApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = facade.login(userName, password);

        if(entity != null) {
            return mapper.map(entity, AccountDetailedDto.class);
        }

        return null;
    }

    @Override
    public void logout(AccountDetailedDto account) throws RemoteException {
        AccountApplicationFacade facade = AccountApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = mapper.map(account, Account.class);
        facade.logout(entity.getId());
    }
}