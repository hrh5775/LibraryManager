package team2.domain.entities;

import javafx.util.Pair;
import team2.domain.enums.properties.AccountProperty;

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

    public String get_password() {
        return _password;
    }

    public AccountRole getAccountRole() {
        return _accountRole;
    }

    public void setId(int id) {
        _id = id;
    }

    public void set_userName(String userName) {
        _userName = userName;
    }

    public void set_password(String password) {
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