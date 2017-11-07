package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class MediaMemberSmallDto extends BaseDtoEntity {
    private int _id;
    private MediaSmallDto _media;
    private String _extendedIndex;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public MediaSmallDto getMedia() {
        return _media;
    }

    public void setMedia(MediaSmallDto media) {
        _media = media;
    }

    public String getExtendedIndex() {
        return _extendedIndex;
    }

    public void setExtendedIndex(String extendedIndex)
    {
        _extendedIndex = extendedIndex;
    }
}