package at.team2.database_wrapper.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Genre", schema = "Library", catalog = "")
public class GenreEntity {
    private int id;
    private String name;
    private Collection<MediaEntity> mediaById;

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
    @Column(name = "name", nullable = false, length = 255)
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

        GenreEntity that = (GenreEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "genreByGenreId")
    public Collection<MediaEntity> getMediaById() {
        return mediaById;
    }

    public void setMediaById(Collection<MediaEntity> mediaById) {
        this.mediaById = mediaById;
    }
}
