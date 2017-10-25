package team2.database_wrapper.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "CreatorPerson", schema = "Library", catalog = "")
public class CreatorPersonEntity {
    private int id;
    private int creatorTypeId;
    private String firstName;
    private String lastName;
    private String academicTitle;
    private Date birthDate;
    private CreatorTypeEntity creatorTypeByCreatorTypeId;
    private Collection<MediaCreatorPersonEntity> mediaCreatorPeopleById;

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
    @Column(name = "creatorTypeId", nullable = false)
    public int getCreatorTypeId() {
        return creatorTypeId;
    }

    public void setCreatorTypeId(int creatorTypeId) {
        this.creatorTypeId = creatorTypeId;
    }

    @Basic
    @Column(name = "firstName", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "academicTitle", nullable = true, length = 45)
    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    @Basic
    @Column(name = "birthDate", nullable = true)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreatorPersonEntity that = (CreatorPersonEntity) o;

        if (id != that.id) return false;
        if (creatorTypeId != that.creatorTypeId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (academicTitle != null ? !academicTitle.equals(that.academicTitle) : that.academicTitle != null)
            return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + creatorTypeId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (academicTitle != null ? academicTitle.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "creatorTypeId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public CreatorTypeEntity getCreatorTypeByCreatorTypeId() {
        return creatorTypeByCreatorTypeId;
    }

    public void setCreatorTypeByCreatorTypeId(CreatorTypeEntity creatorTypeByCreatorTypeId) {
        this.creatorTypeByCreatorTypeId = creatorTypeByCreatorTypeId;
    }

    @OneToMany(mappedBy = "creatorPersonByCreatorPersonId")
    public Collection<MediaCreatorPersonEntity> getMediaCreatorPeopleById() {
        return mediaCreatorPeopleById;
    }

    public void setMediaCreatorPeopleById(Collection<MediaCreatorPersonEntity> mediaCreatorPeopleById) {
        this.mediaCreatorPeopleById = mediaCreatorPeopleById;
    }
}
