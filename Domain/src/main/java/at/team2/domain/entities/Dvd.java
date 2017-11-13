package at.team2.domain.entities;

import at.team2.domain.enums.properties.DvdProperty;
import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Dvd extends BaseDomainEntity<DvdProperty> {
    private int _id;
    private Integer _playingTime;
    private Media _media;

    @Override
    public int getId() {
        return _id;
    }

    public Integer getPlayingTime() {
        return _playingTime;
    }

    public Media getMedia() {
        return _media;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setPlayingTime(Integer playingTime) {
        _playingTime = playingTime;
    }

    public void setMedia(Media media) {
        _media = media;
    }

    @Override
    public List<Pair<DvdProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}