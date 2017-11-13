package at.team2.domain.entities;

import at.team2.domain.enums.properties.MediaMemberProperty;
import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class MediaMember extends BaseDomainEntity<MediaMemberProperty> {
    private int _id;
    private Media _media;
    private String _extendedIndex;

    @Override
    public int getId() {
        return _id;
    }

    public Media getMedia() {
        return _media;
    }

    public String getExtendedIndex() {
        return _extendedIndex;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setMedia(Media media) {
        _media = media;
    }

    public void setExtendedIndex(String extendedIndex) {
        _extendedIndex = extendedIndex;
    }

    @Override
    public List<Pair<MediaMemberProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}