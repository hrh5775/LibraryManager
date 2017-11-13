package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class AccountRoleSmallDto extends BaseDtoEntity {
    private int _id;
    private String _key;
    private String _roleName;

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
}