package at.team2.domain.entities;

import javafx.util.Pair;
import at.team2.domain.enums.properties.ConfigurationProperty;

import java.util.LinkedList;
import java.util.List;

public class Configuration extends BaseDomainEntity<ConfigurationProperty>  {
    private int _id;
    private String _identifier;
    private String _data;

    @Override
    public int getID() {
        return _id;
    }

    public String getIdentifier() {
        return _identifier;
    }

    public String getData() {
        return _data;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setIdentifier(String identifier) {
        _identifier = identifier;
    }

    public void setData(String data) {
        _data = data;
    }

    @Override
    public List<Pair<String, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}