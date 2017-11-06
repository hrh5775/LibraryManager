package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class MediaMemberDto extends BaseDtoEntity {
    private int _id;
    private int _mediaId;
    private String _extendedIndex;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getMediaId() {
        return _mediaId;
    }

    public void setMediaId(int mediaId) {
        _mediaId = mediaId;
    }

    public String getExtendedIndex() {
        return _extendedIndex;
    }

    public void setExtendedIndex(String extendedIndex)
    {
        _extendedIndex = extendedIndex;
    }
}
