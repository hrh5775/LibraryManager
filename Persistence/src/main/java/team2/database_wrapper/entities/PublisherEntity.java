package team2.database_wrapper.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Publisher", schema = "Library", catalog = "")
public class PublisherEntity {
    private int id;
    private int publisherTypeId;
    private String name;
    private String address;
    private Collection<MediaEntity> mediaById;
    private PublisherTypeEntity publisherTypeByPublisherTypeId;

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
    @Column(name = "publisherTypeId", nullable = false)
    public int getPublisherTypeId() {
        return publisherTypeId;
    }

    public void setPublisherTypeId(int publisherTypeId) {
        this.publisherTypeId = publisherTypeId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublisherEntity that = (PublisherEntity) o;

        if (id != that.id) return false;
        if (publisherTypeId != that.publisherTypeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + publisherTypeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "publisherByPublisherId")
    public Collection<MediaEntity> getMediaById() {
        return mediaById;
    }

    public void setMediaById(Collection<MediaEntity> mediaById) {
        this.mediaById = mediaById;
    }

    @ManyToOne
    @JoinColumn(name = "publisherTypeId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PublisherTypeEntity getPublisherTypeByPublisherTypeId() {
        return publisherTypeByPublisherTypeId;
    }

    public void setPublisherTypeByPublisherTypeId(PublisherTypeEntity publisherTypeByPublisherTypeId) {
        this.publisherTypeByPublisherTypeId = publisherTypeByPublisherTypeId;
    }
}
