package at.team2.server.common;

import at.team2.application.facade.AccountApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.domain.entities.Account;
import org.modelmapper.ModelMapper;

public class AccountBase {
    private AccountApplicationFacade _accountFacade;

    private AccountApplicationFacade getAccountFacade() {
        if(_accountFacade == null) {
            _accountFacade = new AccountApplicationFacade();
        }

        return _accountFacade;
    }

    public AccountDetailedDto doLogin(String userName, String password) {
        AccountApplicationFacade facade = getAccountFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = facade.login(userName, password);

        if(entity != null) {
            return mapper.map(entity, AccountDetailedDto.class);
        }

        return null;
    }

    public void doLogout(AccountDetailedDto account) {
        AccountApplicationFacade facade = getAccountFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = mapper.map(account, Account.class);
        facade.logout(entity.getId());
    }

    public void close() {
        if(_accountFacade != null) {
            _accountFacade.closeDbSession();
        }
    }
}