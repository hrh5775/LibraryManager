package at.team2.database_wrapper.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "MediaMember", schema = "Library", catalog = "")
public class MediaMemberEntity {
    private int id;
    private int mediaId;
    private String extendedIndex;
    private Collection<LoanEntity> loansById;
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
    @Column(name = "extendedIndex", nullable = false, length = 45)
    public String getExtendedIndex() {
        return extendedIndex;
    }

    public void setExtendedIndex(String extendedIndex) {
        this.extendedIndex = extendedIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaMemberEntity that = (MediaMemberEntity) o;

        if (id != that.id) return false;
        if (mediaId != that.mediaId) return false;
        if (extendedIndex != null ? !extendedIndex.equals(that.extendedIndex) : that.extendedIndex != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mediaId;
        result = 31 * result + (extendedIndex != null ? extendedIndex.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "mediaMemberByMediaMemberId")
    public Collection<LoanEntity> getLoansById() {
        return loansById;
    }

    public void setLoansById(Collection<LoanEntity> loansById) {
        this.loansById = loansById;
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
