package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class CustomerSmallDto extends BaseDtoEntity {
    private int _id;
    private String _firstName;
    private String _lastName;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }
}
