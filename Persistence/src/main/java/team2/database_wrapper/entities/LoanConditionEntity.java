package team2.database_wrapper.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "LoanCondition", schema = "Library", catalog = "")
public class LoanConditionEntity {
    private int id;
    private int loanTerm;
    private int extension;
    private Collection<MediaTypeEntity> mediaTypesById;

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
    @Column(name = "loanTerm", nullable = false)
    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    @Basic
    @Column(name = "extension", nullable = false)
    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoanConditionEntity that = (LoanConditionEntity) o;

        if (id != that.id) return false;
        if (loanTerm != that.loanTerm) return false;
        if (extension != that.extension) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + loanTerm;
        result = 31 * result + extension;
        return result;
    }

    @OneToMany(mappedBy = "loanConditionByLoanConditionId")
    public Collection<MediaTypeEntity> getMediaTypesById() {
        return mediaTypesById;
    }

    public void setMediaTypesById(Collection<MediaTypeEntity> mediaTypesById) {
        this.mediaTypesById = mediaTypesById;
    }
}
