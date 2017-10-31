package at.team2.connector.dto.small;

import at.team2.connector.interfaces.BaseDtoEntity;

import java.sql.Date;

public class MediaSmallDto extends BaseDtoEntity {
    private int _mediaId;
    private boolean _available;
    private String _title;
    private Date _publishedDate;
    private String _description;
    private byte[] _cover;
//    private MediaType mediaType; // @todo: required?

    public int getMediaId() {
        return _mediaId;
    }

    public boolean getAvailable() {
        return _available;
    }

    public String getTitle() {
        return _title;
    }

    public Date getPublishedDate() {
        return _publishedDate;
    }

    public String getDescription() {
        return _description;
    }

    public byte[] getCover() {
        return _cover;
    }

    public void setMediaId(int mediaId) {
        _mediaId = mediaId;
    }

    public void setAvailable(boolean available) {
        _available = available;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public void setPublishedDate(Date publishedDate) {
        _publishedDate = publishedDate;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public void setCover(byte[] cover) {
        _cover = cover;
    }
}