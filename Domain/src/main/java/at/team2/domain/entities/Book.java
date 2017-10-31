package at.team2.domain.entities;

import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;
import at.team2.domain.enums.properties.BookProperty;

import java.util.LinkedList;
import java.util.List;

public class Book extends BaseDomainEntity<BookProperty> {
    private int _id;
    private Integer _edition;
    private Media _media;

    @Override
    public int getID() {
        return _id;
    }

    public Integer getEdition() {
        return _edition;
    }

    public Media getMedia() {
        return _media;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setEdition(Integer edition) {
        _edition = edition;
    }

    public void setMedia(Media media) {
        _media = media;
    }

    @Override
    public List<Pair<BookProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}