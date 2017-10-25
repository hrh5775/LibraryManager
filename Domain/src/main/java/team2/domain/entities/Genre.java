package team2.domain.entities;

import javafx.util.Pair;
import team2.domain.enums.properties.GenreProperty;

import java.util.LinkedList;
import java.util.List;

public class Genre extends BaseDomainEntity<GenreProperty>  {
    private int _id;
    private String _name;

    @Override
    public int getID() {
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

    @Override
    public List<Pair<String, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}