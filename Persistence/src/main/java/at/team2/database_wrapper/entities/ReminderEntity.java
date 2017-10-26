package at.team2.database_wrapper.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "Reminder", schema = "Library", catalog = "")
public class ReminderEntity {
    private int id;
    private Date reminderDate;
    private Integer reminderCount;
    private Collection<LoanEntity> loansById;

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
    @Column(name = "reminderDate", nullable = true)
    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    @Basic
    @Column(name = "reminderCount", nullable = true)
    public Integer getReminderCount() {
        return reminderCount;
    }

    public void setReminderCount(Integer reminderCount) {
        this.reminderCount = reminderCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReminderEntity that = (ReminderEntity) o;

        if (id != that.id) return false;
        if (reminderDate != null ? !reminderDate.equals(that.reminderDate) : that.reminderDate != null) return false;
        if (reminderCount != null ? !reminderCount.equals(that.reminderCount) : that.reminderCount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (reminderDate != null ? reminderDate.hashCode() : 0);
        result = 31 * result + (reminderCount != null ? reminderCount.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reminderByReminderId")
    public Collection<LoanEntity> getLoansById() {
        return loansById;
    }

    public void setLoansById(Collection<LoanEntity> loansById) {
        this.loansById = loansById;
    }
}
