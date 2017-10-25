package team2.database_wrapper.entities;

import javax.persistence.*;

@Entity
@Table(name = "DVDMeta", schema = "Library", catalog = "")
public class DvdMetaEntity {
    private int id;
    private int mediaId;
    private Integer playingTime;
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
    @Column(name = "playingTime", nullable = true)
    public Integer getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(Integer playingTime) {
        this.playingTime = playingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DvdMetaEntity that = (DvdMetaEntity) o;

        if (id != that.id) return false;
        if (mediaId != that.mediaId) return false;
        if (playingTime != null ? !playingTime.equals(that.playingTime) : that.playingTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mediaId;
        result = 31 * result + (playingTime != null ? playingTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "mediaId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public MediaEntity getMediaByMediaId() {
        return mediaByMediaId;
    }

    public void setMediaByMediaId(MediaEntity mediaByMediaId) {
        this.mediaByMediaId = mediaByMediaId;
    }
}
