package at.team2.domain.entities;

import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;
import at.team2.domain.enums.properties.ReservationProperty;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Reservation extends BaseDomainEntity<ReservationProperty> {
    private int _id;
    private Date _reservationDate;
    private Date _informationDate;
    private byte _closed;
    private Customer _customer;
    private Media _media;

    @Override
    public int getID() {
        return _id;
    }

    public Date getReservationDate() {
        return _reservationDate;
    }

    public Date getInformationDate() {
        return _informationDate;
    }

    public byte getClosed() {
        return _closed;
    }

    public Customer getCustomer() {
        return _customer;
    }

    public Media getMedia() {
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

    public void setClosed(byte closed) {
        _closed = closed;
    }

    public void setCustomer(Customer customer) {
        _customer = customer;
    }

    public void setMedia(Media media) {
        _media = media;
    }

    @Override
    public List<Pair<ReservationProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}