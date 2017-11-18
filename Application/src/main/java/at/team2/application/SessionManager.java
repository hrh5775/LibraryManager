package at.team2.application;

import at.team2.common.dto.detailed.AccountDetailedDto;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static SessionManager _instance;
    private Map<String, AccountDetailedDto> _accounts;// concurrent list

    private SessionManager() {
        _accounts = Collections.synchronizedMap(new HashMap<>());
    }

    public static SessionManager getInstance() {
        if(_instance == null) {
            _instance = new SessionManager();
        }

        return _instance;
    }

    public void addAccount(AccountDetailedDto account) {
        String tmp = account.getUserName() + account.getId();

        if(_accounts.get(tmp) == null) {
            _accounts.put(account.getUserName() + account.getId(), account);
        }
    }

    public void removeSession(AccountDetailedDto account) {
        String tmp = account.getUserName() + account.getId();

        if(_accounts.get(tmp) == null) {
            _accounts.remove(account.getUserName() + account.getId(), account);
        }
    }

    public boolean isSessionAvailable(AccountDetailedDto account) {
        return getSession(account) != null;
    }

    public AccountDetailedDto getSession(AccountDetailedDto account) {
        return _accounts.get(account.getUserName() + account.getId());
    }
}