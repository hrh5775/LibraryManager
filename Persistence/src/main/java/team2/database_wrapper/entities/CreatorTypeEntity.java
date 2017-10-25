package team2.database_wrapper.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "CreatorType", schema = "Library", catalog = "")
public class CreatorTypeEntity {
    private int id;
    private String typeName;
    private Collection<CreatorPersonEntity> creatorPeopleById;

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
    @Column(name = "typeName", nullable = false, length = 255)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreatorTypeEntity that = (CreatorTypeEntity) o;

        if (id != that.id) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "creatorTypeByCreatorTypeId")
    public Collection<CreatorPersonEntity> getCreatorPeopleById() {
        return creatorPeopleById;
    }

    public void setCreatorPeopleById(Collection<CreatorPersonEntity> creatorPeopleById) {
        this.creatorPeopleById = creatorPeopleById;
    }
}
