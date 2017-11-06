package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

public class MediaMemberDto extends BaseDtoEntity
{
    private int _id;
    private int _mediaId;
    private String _extendedIndex;

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public int get_mediaId()
    {
        return _mediaId;
    }

    public void set_mediaId(int _mediaId)
    {
        this._mediaId = _mediaId;
    }

    public String get_extendedIndex()
    {
        return _extendedIndex;
    }

    public void set_extendedIndex(String _extendedIndex)
    {
        this._extendedIndex = _extendedIndex;
    }

}
