package at.team2.common.interfaces.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import javax.ejb.Remote;

@Remote
public interface AccountRemote {
    public AccountDetailedDto login(String userName, String password);
    public void logout(AccountDetailedDto account);
}