package at.team2.common.dto.detailed;

import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.interfaces.BaseDtoEntity;
import java.sql.Date;

public class ReservationDetailedDto extends BaseDtoEntity {
    private int _id;
    private Date _reservationDate;
    private Date _informationDate;
    private boolean _closed;
    private CustomerSmallDto _customer;
    private MediaSmallDto _media;

    public int getId() {
        return _id;
    }

    public Date getReservationDate() {
        return _reservationDate;
    }

    public Date getInformationDate() {
        return _informationDate;
    }

    public boolean getClosed() {
        return _closed;
    }

    public CustomerSmallDto getCustomer() {
        return _customer;
    }

    public MediaSmallDto getMedia() {
        return _media;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setReservationDate(Date reservationDate) {
        _reservationDate = reservationDate;
    }

    public void setInformationDate(Date informationDate) {
        _informationDate = informationDate;
    }

    public void setClosed(boolean closed) {
        _closed = closed;
    }

    public void setCustomer(CustomerSmallDto customer) {
        _customer = customer;
    }

    public void setMedia(MediaSmallDto media) {
        _media = media;
    }
}