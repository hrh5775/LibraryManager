package at.team2.database_wrapper.entities;

import javax.persistence.*;

@Entity
@Table(name = "BookMeta", schema = "Library", catalog = "")
public class BookMetaEntity {
    private int id;
    private int mediaId;
    private Integer edition;
    private MediaEntity mediaByMediaId;

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
    @Column(name = "edition", nullable = true)
    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookMetaEntity that = (BookMetaEntity) o;

        if (id != that.id) return false;
        if (mediaId != that.mediaId) return false;
        if (edition != null ? !edition.equals(that.edition) : that.edition != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mediaId;
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
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
}
