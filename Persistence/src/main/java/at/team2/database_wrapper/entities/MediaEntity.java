package at.team2.database_wrapper.entities;

import at.team2.database_wrapper.helper.TypeHelper;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "Media", schema = "Library", catalog = "")
public class MediaEntity {
    private int id;
    private int mediaTypeId;
    private String standardMediaId;
    private String baseIndex;
    private byte available;
    private String title;
    private int genreId;
    private int publisherId;
    private Date publishedDate;
    private String description;
    private byte[] cover;
    private String comment;
    private Collection<BookMetaEntity> bookMetasById;
    private Collection<DvdMetaEntity> dvdMetasById;
    private MediaTypeEntity mediaTypeByMediaTypeId;
    private GenreEntity genreByGenreId;
    private PublisherEntity publisherByPublisherId;
    private Collection<MediaMemberEntity> mediaMembersById;
    private Collection<MediaCreatorPersonEntity> mediaCreatorPeopleById;
    private Collection<ReservationEntity> reservationsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "mediaTypeId", nullable = false)
    public int getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(int mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    @Basic
    @Column(name = "standardMediaId", nullable = false, length = 45)
    public String getStandardMediaId() {
        return standardMediaId;
    }

    public void setStandardMediaId(String standardMediaId) {
        this.standardMediaId = standardMediaId;
    }

    @Basic
    @Column(name = "baseIndex", nullable = false, length = 15)
    public String getBaseIndex() {
        return baseIndex;
    }

    public void setBaseIndex(String baseIndex) {
        this.baseIndex = baseIndex;
    }

    @Basic
    @Column(name = "available", nullable = false)
    public boolean getAvailable() {
        return TypeHelper.toBoolean(available);
    }

    public void setAvailable(boolean available) {
        this.available = TypeHelper.toByte(available);
    }

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "genreId", nullable = false)
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Basic
    @Column(name = "publisherId", nullable = false)
    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "publishedDate", nullable = false)
    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "cover", nullable = true)
    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    @Basic
    @Column(name = "comment", nullable = true, length = 255)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaEntity that = (MediaEntity) o;

        if (id != that.id) return false;
        if (mediaTypeId != that.mediaTypeId) return false;
        if (available != that.available) return false;
        if (genreId != that.genreId) return false;
        if (publisherId != that.publisherId) return false;
        if (standardMediaId != null ? !standardMediaId.equals(that.standardMediaId) : that.standardMediaId != null)
            return false;
        if (baseIndex != null ? !baseIndex.equals(that.baseIndex) : that.baseIndex != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (publishedDate != null ? !publishedDate.equals(that.publishedDate) : that.publishedDate != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!Arrays.equals(cover, that.cover)) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mediaTypeId;
        result = 31 * result + (standardMediaId != null ? standardMediaId.hashCode() : 0);
        result = 31 * result + (baseIndex != null ? baseIndex.hashCode() : 0);
        result = 31 * result + (int) available;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + genreId;
        result = 31 * result + publisherId;
        result = 31 * result + (publishedDate != null ? publishedDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(cover);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "mediaByMediaId", cascade = CascadeType.REFRESH)
    public Collection<BookMetaEntity> getBookMetasById() {
        return bookMetasById;
    }

    public void setBookMetasById(Collection<BookMetaEntity> bookMetasById) {
        this.bookMetasById = bookMetasById;
    }

    @OneToMany(mappedBy = "mediaByMediaId", cascade = CascadeType.REFRESH)
    public Collection<DvdMetaEntity> getDvdMetasById() {
        return dvdMetasById;
    }

    public void setDvdMetasById(Collection<DvdMetaEntity> dvdMetasById) {
        this.dvdMetasById = dvdMetasById;
    }

    @ManyToOne
    @JoinColumn(name = "mediaTypeId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public MediaTypeEntity getMediaTypeByMediaTypeId() {
        return mediaTypeByMediaTypeId;
    }

    public void setMediaTypeByMediaTypeId(MediaTypeEntity mediaTypeByMediaTypeId) {
        this.mediaTypeByMediaTypeId = mediaTypeByMediaTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "genreId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public GenreEntity getGenreByGenreId() {
        return genreByGenreId;
    }

    public void setGenreByGenreId(GenreEntity genreByGenreId) {
        this.genreByGenreId = genreByGenreId;
    }

    @ManyToOne
    @JoinColumn(name = "publisherId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PublisherEntity getPublisherByPublisherId() {
        return publisherByPublisherId;
    }

    public void setPublisherByPublisherId(PublisherEntity publisherByPublisherId) {
        this.publisherByPublisherId = publisherByPublisherId;
    }

    @OneToMany(mappedBy = "mediaByMediaId", cascade = CascadeType.REFRESH)
    public Collection<MediaMemberEntity> getMediaMembersById() {
        return mediaMembersById;
    }

    public void setMediaMembersById(Collection<MediaMemberEntity> mediaMembersById) {
        this.mediaMembersById = mediaMembersById;
    }

    @OneToMany(mappedBy = "mediaByMediaId")
    public Collection<MediaCreatorPersonEntity> getMediaCreatorPeopleById() {
        return mediaCreatorPeopleById;
    }

    public void setMediaCreatorPeopleById(Collection<MediaCreatorPersonEntity> mediaCreatorPeopleById) {
        this.mediaCreatorPeopleById = mediaCreatorPeopleById;
    }

    @OneToMany(mappedBy = "mediaByMediaId")
    public Collection<ReservationEntity> getReservationsById() {
        return reservationsById;
    }

    public void setReservationsById(Collection<ReservationEntity> reservationsById) {
        this.reservationsById = reservationsById;
    }
}
