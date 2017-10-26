package at.team2.database_wrapper.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "PublisherType", schema = "Library", catalog = "")
public class PublisherTypeEntity {
    private int id;
    private String typeName;
    private Collection<PublisherEntity> publishersById;

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
    @Column(name = "typeName", nullable = false, length = 45)
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

        PublisherTypeEntity that = (PublisherTypeEntity) o;

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

    @OneToMany(mappedBy = "publisherTypeByPublisherTypeId")
    public Collection<PublisherEntity> getPublishersById() {
        return publishersById;
    }

    public void setPublishersById(Collection<PublisherEntity> publishersById) {
        this.publishersById = publishersById;
    }
}
