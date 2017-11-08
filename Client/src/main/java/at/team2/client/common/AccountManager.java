package at.team2.client.common;

import at.team2.common.dto.detailed.AccountDetailedDto;

public class AccountManager {
    private static AccountManager _accountManager;
    private AccountDetailedDto _account;

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if(_accountManager == null) {
            _accountManager = new AccountManager();
        }

        return _accountManager;
    }

    public static void reset() {
        _accountManager = null;
    }

    public void setAccount(AccountDetailedDto account) {
        _account = account;
    }

    public AccountDetailedDto getAccount() {
        return _account;
    }
}