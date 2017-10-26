package at.team2.database_wrapper.entities;

import at.team2.database_wrapper.helper.TypeHelper;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Reservation", schema = "Library", catalog = "")
public class ReservationEntity {
    private int id;
    private int customerId;
    private int mediaId;
    private Date reservationDate;
    private Date informationDate;
    private byte closed;
    private CustomerEntity customerByCustomerId;
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
    @Column(name = "customerId", nullable = false)
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
    @Column(name = "reservationDate", nullable = false)
    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Basic
    @Column(name = "informationDate", nullable = true)
    public Date getInformationDate() {
        return informationDate;
    }

    public void setInformationDate(Date informationDate) {
        this.informationDate = informationDate;
    }

    @Basic
    @Column(name = "closed", nullable = false)
    public boolean getClosed() {
        return TypeHelper.toBoolean(closed);
    }

    public void setClosed(boolean closed) {
        this.closed = TypeHelper.toByte(closed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationEntity that = (ReservationEntity) o;

        if (id != that.id) return false;
        if (customerId != that.customerId) return false;
        if (mediaId != that.mediaId) return false;
        if (closed != that.closed) return false;
        if (reservationDate != null ? !reservationDate.equals(that.reservationDate) : that.reservationDate != null)
            return false;
        if (informationDate != null ? !informationDate.equals(that.informationDate) : that.informationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerId;
        result = 31 * result + mediaId;
        result = 31 * result + (reservationDate != null ? reservationDate.hashCode() : 0);
        result = 31 * result + (informationDate != null ? informationDate.hashCode() : 0);
        result = 31 * result + (int) closed;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public CustomerEntity getCustomerByCustomerId() {
        return customerByCustomerId;
    }

    public void setCustomerByCustomerId(CustomerEntity customerByCustomerId) {
        this.customerByCustomerId = customerByCustomerId;
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
