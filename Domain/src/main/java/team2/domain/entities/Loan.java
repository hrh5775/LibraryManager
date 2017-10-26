package team2.domain.entities;

import javafx.util.Pair;
import team2.domain.enums.properties.LoanProperty;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Loan extends BaseDomainEntity<LoanProperty>  {
    private int _id;
    private Date _start;
    private Date _lastRenewalStart;
    private Date _end;
    private boolean _closed;
    private Customer _customer;
    private MediaMember _mediaMember;
    private Reminder _reminder;

    @Override
    public int getID() {
        return _id;
    }

    public Date getStart() {
        return _start;
    }

    public Date getLastRenewalStart() {
        return _lastRenewalStart;
    }

    public Date getEnd() {
        return _end;
    }

    public boolean getClosed() {
        return _closed;
    }

    public Customer getCustomer() {
        return _customer;
    }

    public MediaMember getMediaMember() {
        return _mediaMember;
    }

    public Reminder getReminder() {
        return _reminder;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setStart(Date start) {
        _start = start;
    }

    public void setLastRenewalStart(Date lastRenewalStart) {
        _lastRenewalStart = lastRenewalStart;
    }

    public void setEnd(Date end) {
        _end = end;
    }

    public void setClosed(boolean closed) {
        _closed = closed;
    }

    public void setCustomer(Customer customer) {
        _customer = customer;
    }

    public void setMediaMember(MediaMember mediaMember) {
        _mediaMember = mediaMember;
    }

    public void setReminder(Reminder reminder) {
        _reminder = reminder;
    }

    @Override
    public List<Pair<String, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}