package at.team2.domain.entities;

import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;
import at.team2.domain.enums.properties.MediaProperty;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Media extends BaseDomainEntity<MediaProperty> {
    private int _id;
    private String _standardMediaId;
    private String _baseIndex;
    private boolean _available;
    private String _title;
    private Date _publishedDate;
    private String _description;
    private byte[] _cover;
    private String _comment;
    private MediaType _mediaType;
    private Genre _genre;
    private Publisher _publisher;
    private List<CreatorPerson> _creatorPersons;

    @Override
    public int getId() {
        return _id;
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

    public MediaType getMediaType() {
        return _mediaType;
    }

    public Genre getGenre() {
        return _genre;
    }

    public Publisher getPublisher() {
        return _publisher;
    }

    public List<CreatorPerson> getCreatorPersons() {
        return _creatorPersons;
    }

    public void setId(int id) {
        _id = id;
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

    public void setMediaType(MediaType mediaType) {
        _mediaType = mediaType;
    }

    public void setGenre(Genre genre) {
        _genre = genre;
    }

    public void setPublisher(Publisher publisher) {
        _publisher = publisher;
    }

    public void setCreatorPersons(List<CreatorPerson> creatorPersons) {
        _creatorPersons = creatorPersons;
    }

    @Override
    public List<Pair<MediaProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}