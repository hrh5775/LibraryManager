package at.team2.domain.entities;

import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;
import at.team2.domain.enums.properties.CreatorTypeProperty;

import java.util.LinkedList;
import java.util.List;

public class CreatorType extends BaseDomainEntity<CreatorTypeProperty> {
    private int _id;
    private String _typeName;

    @Override
    public int getId() {
        return _id;
    }

    public String getTypeName() {
        return _typeName;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setTypeName(String typeName) {
        _typeName = typeName;
    }

    @Override
    public List<Pair<CreatorTypeProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}