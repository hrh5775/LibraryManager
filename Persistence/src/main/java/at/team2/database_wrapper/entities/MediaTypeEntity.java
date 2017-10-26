package at.team2.database_wrapper.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "MediaType", schema = "Library", catalog = "")
public class MediaTypeEntity {
    private int id;
    private int loanConditionId;
    private String name;
    private Collection<MediaEntity> mediaById;
    private LoanConditionEntity loanConditionByLoanConditionId;

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
    @Column(name = "loanConditionId", nullable = false)
    public int getLoanConditionId() {
        return loanConditionId;
    }

    public void setLoanConditionId(int loanConditionId) {
        this.loanConditionId = loanConditionId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaTypeEntity that = (MediaTypeEntity) o;

        if (id != that.id) return false;
        if (loanConditionId != that.loanConditionId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + loanConditionId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "mediaTypeByMediaTypeId")
    public Collection<MediaEntity> getMediaById() {
        return mediaById;
    }

    public void setMediaById(Collection<MediaEntity> mediaById) {
        this.mediaById = mediaById;
    }

    @ManyToOne
    @JoinColumn(name = "loanConditionId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public LoanConditionEntity getLoanConditionByLoanConditionId() {
        return loanConditionByLoanConditionId;
    }

    public void setLoanConditionByLoanConditionId(LoanConditionEntity loanConditionByLoanConditionId) {
        this.loanConditionByLoanConditionId = loanConditionByLoanConditionId;
    }
}
