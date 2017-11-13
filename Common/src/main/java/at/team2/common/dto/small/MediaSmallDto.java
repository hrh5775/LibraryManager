package at.team2.common.dto.small;

import at.team2.common.interfaces.BaseDtoEntity;

import java.sql.Date;

public class MediaSmallDto extends BaseDtoEntity {
    private int _mediaId;
    private String _standardMediaId;
    private boolean _available;
    private String _title;
    private Date _publishedDate;
    private String _description;
    private String _baseIndex;
    private PublisherSmallDto _publisher;
    private MediaTypeSmallDto _mediaType;

    public int getMediaId() {
        return _mediaId;
    }

    public String getStandardMediaId() {
        return _standardMediaId;
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

    public String getBaseIndex() {
        return _baseIndex;
    }

    public PublisherSmallDto getPublisher() {
        return _publisher;
    }

    public MediaTypeSmallDto getMediaType() {
        return _mediaType;
    }

    public void setMediaId(int mediaId) {
        _mediaId = mediaId;
    }

    public void setStandardMediaId(String standardMediaId) {
        _standardMediaId = standardMediaId;
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

    public void setBaseIndex(String baseIndex) {
        _baseIndex = baseIndex;
    }

    public void setPublisher(PublisherSmallDto publisher) {
        _publisher = publisher;
    }

    public void setMediaType(MediaTypeSmallDto mediaType) {
        _mediaType = mediaType;
    }

    @Override
    public String toString() {
        return _title;
    }
}