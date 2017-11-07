package at.team2.common.dto.detailed;

import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.common.dto.small.GenreSmallDto;
import at.team2.common.dto.small.MediaTypeSmallDto;
import at.team2.common.dto.small.PublisherSmallDto;
import at.team2.common.interfaces.BaseDtoEntity;

import java.sql.Date;
import java.util.List;

public class MediaDetailedDto extends BaseDtoEntity {
    private int _mediaId;
    private String _standardMediaId;
    private String _baseIndex;
    private boolean _available;
    private String _title;
    private Date _publishedDate;
    private String _description;
    private byte[] _cover;
    private String _comment;
    private MediaTypeSmallDto _mediaType;
    private GenreSmallDto _genre;
    private PublisherSmallDto _publisher;
    private List<CreatorPersonSmallDto> _creatorPersons;

    public int getMediaId() {
        return _mediaId;
    }

    public String getStandardMediaId() {
        return _standardMediaId;
    }

    public String getBaseIndex() {
        return _baseIndex;
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

    public String getComment() {
        return _comment;
    }

    public MediaTypeSmallDto getMediaType() {
        return _mediaType;
    }

    public GenreSmallDto getGenre() {
        return _genre;
    }

    public PublisherSmallDto getPublisher() {
        return _publisher;
    }

    public List<CreatorPersonSmallDto> getCreatorPersons() {
        return _creatorPersons;
    }

    public void setMediaId(int id) {
        _mediaId = id;
    }

    public void setStandardMediaId(String standardMediaId) {
        _standardMediaId = standardMediaId;
    }

    public void setBaseIndex(String baseIndex) {
        _baseIndex = baseIndex;
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

    public void setComment(String comment) {
        _comment = comment;
    }

    public void setMediaType(MediaTypeSmallDto mediaType) {
        _mediaType = mediaType;
    }

    public void setGenre(GenreSmallDto genre) {
        _genre = genre;
    }

    public void setPublisher(PublisherSmallDto publisher) {
        _publisher = publisher;
    }

    public void setCreatorPersons(List<CreatorPersonSmallDto> creatorPersons) {
        _creatorPersons = creatorPersons;
    }
}