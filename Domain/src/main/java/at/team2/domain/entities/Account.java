package at.team2.domain.entities;

import at.team2.domain.enums.properties.AccountProperty;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Account extends BaseDomainEntity<AccountProperty>  {
    private int _id;
    private String _userName;
    private String _password;
    private AccountRole _accountRole;

    @Override
    public int getID() {
        return _id;
    }

    public String getUserName() {
        return _userName;
    }

    public String getPassword() {
        return _password;
    }

    public AccountRole getAccountRole() {
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

    public void setAccountRole(AccountRole accountRole) {
        _accountRole = accountRole;
    }

    @Override
    public List<Pair<String, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}