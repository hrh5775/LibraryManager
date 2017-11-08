package at.team2.common.dto.detailed;

import at.team2.common.dto.small.AccountRoleSmallDto;
import at.team2.common.interfaces.BaseDtoEntity;

public class AccountDetailedDto extends BaseDtoEntity {
    private int _id;
    private String _userName;
    private String _password;
    private AccountRoleSmallDto _accountRole;

    public int getId() {
        return _id;
    }

    public String getUserName() {
        return _userName;
    }

    public String getPassword() {
        return _password;
    }

    public AccountRoleSmallDto getAccountRole() {
        return _accountRole;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setUserName(String userName) {
        _userName = userName;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public void setAccountRole(AccountRoleSmallDto accountRole) {
        _accountRole = accountRole;
    }
}