package at.team2.database_wrapper.entities;

import javax.persistence.*;

@Entity
@Table(name = "Media_CreatorPerson", schema = "Library", catalog = "")
public class MediaCreatorPersonEntity {
    private int id;
    private int mediaId;
    private int creatorPersonId;
    private MediaEntity mediaByMediaId;
    private CreatorPersonEntity creatorPersonByCreatorPersonId;

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
    @Column(name = "mediaId", nullable = false)
    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    @Basic
    @Column(name = "creatorPersonId", nullable = false)
    public int getCreatorPersonId() {
        return creatorPersonId;
    }

    public void setCreatorPersonId(int creatorPersonId) {
        this.creatorPersonId = creatorPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaCreatorPersonEntity that = (MediaCreatorPersonEntity) o;

        if (id != that.id) return false;
        if (mediaId != that.mediaId) return false;
        if (creatorPersonId != that.creatorPersonId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mediaId;
        result = 31 * result + creatorPersonId;
        return result;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "mediaId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public MediaEntity getMediaByMediaId() {
        return mediaByMediaId;
    }

    public void setMediaByMediaId(MediaEntity mediaByMediaId) {
        this.mediaByMediaId = mediaByMediaId;
    }

    @ManyToOne
    @JoinColumn(name = "creatorPersonId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public CreatorPersonEntity getCreatorPersonByCreatorPersonId() {
        return creatorPersonByCreatorPersonId;
    }

    public void setCreatorPersonByCreatorPersonId(CreatorPersonEntity creatorPersonByCreatorPersonId) {
        this.creatorPersonByCreatorPersonId = creatorPersonByCreatorPersonId;
    }
}
