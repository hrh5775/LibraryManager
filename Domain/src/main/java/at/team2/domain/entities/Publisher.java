package at.team2.domain.entities;

import at.team2.domain.enums.properties.PublisherProperty;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Publisher extends BaseDomainEntity<PublisherProperty>  {
    private int _id;
    private String _name;
    private String _address;
    private PublisherType _publisherType;

    @Override
    public int getID() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getAddress() {
        return _address;
    }

    public PublisherType getPublisherType() {
        return _publisherType;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setAddress(String address) {
        _address = address;
    }

    public void setPublisherType(PublisherType publisherType) {
        _publisherType = publisherType;
    }

    @Override
    public List<Pair<String, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}