package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class CreatorPersonSmallDto extends BaseDtoEntity {
    private int _id;
    private String _firstName;
    private String _lastName;

    public int getId() {
        return _id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }
}