package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class AccountSmallDto extends BaseDtoEntity {
    private int _id;
    private String _userName;
    private String _password;

    public int getId() {
        return _id;
    }

    public String getUserName() {
        return _userName;
    }

    public String getPassword() {
        return _password;
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
}