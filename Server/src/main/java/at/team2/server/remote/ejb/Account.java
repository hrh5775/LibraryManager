package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.ejb.AccountRemote;
import at.team2.server.common.AccountBase;

import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.Serializable;

@Stateless
@WebService
@Remote(AccountRemote.class)
public class Account extends AccountBase implements AccountRemote, Serializable {
    @WebMethod
    @Override
    public AccountDetailedDto login(String userName, String password) {
        return doLogin(userName, password);
    }

    @WebMethod
    @Override
    public void logout(AccountDetailedDto account) {
        doLogout(account);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}