package at.team2.domain.entities;

import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;
import at.team2.domain.enums.properties.AccountRoleProperty;

import java.util.LinkedList;
import java.util.List;

public class AccountRole extends BaseDomainEntity<AccountRoleProperty> {
    private int _id;
    private String _key;
    private String _roleName;

    @Override
    public int getId() {
        return _id;
    }

    public String getKey() {
        return _key;
    }

    public String getRoleName() {
        return _roleName;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setKey(String key) {
        _key = key;
    }

    public void setRoleName(String roleName) {
        _roleName = roleName;
    }

    @Override
    public List<Pair<AccountRoleProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}