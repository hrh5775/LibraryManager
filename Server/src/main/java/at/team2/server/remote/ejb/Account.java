package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.ejb.AccountRemote;
import at.team2.server.common.AccountBase;

import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;

@Stateless
@Local
@Remote(AccountRemote.class)
public class Account extends AccountBase implements AccountRemote, Serializable {
    @Override
    public AccountDetailedDto login(String userName, String password) {
        return doLogin(userName, password);
    }

    @Override
    public void logout(AccountDetailedDto account) {
        doLogout(account);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}