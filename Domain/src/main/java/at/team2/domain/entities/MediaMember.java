package at.team2.domain.entities;

import at.team2.domain.enums.properties.MediaMemberProperty;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class MediaMember extends BaseDomainEntity<MediaMemberProperty>  {
    private int _id;
    private int _mediaId;
    private String _extendedIndex;

    @Override
    public int getID() {
        return _id;
    }

    public int getMediaId() {
        return _mediaId;
    }

    public String getExtendedIndex() {
        return _extendedIndex;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setMediaId(int mediaId) {
        _mediaId = mediaId;
    }

    public void setExtendedIndex(String extendedIndex) {
        _extendedIndex = extendedIndex;
    }

    @Override
    public List<Pair<String, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}