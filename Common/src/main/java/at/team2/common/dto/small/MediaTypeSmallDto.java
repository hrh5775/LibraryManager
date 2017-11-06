package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class MediaTypeSmallDto extends BaseDtoEntity {
    private int _id;
    private String _name;

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }
}