package at.team2.common.dto.detailed;

import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.dto.small.ReminderSmallDto;
import at.team2.common.interfaces.BaseDtoEntity;

import java.sql.Date;

public class LoanDetailedDto extends BaseDtoEntity {
    private int _id;
    private Date _start;
    private Date _lastRenewalStart;
    private Date _end;
    private boolean _closed;
    private CustomerSmallDto _customer;
    private MediaMemberSmallDto _mediaMember;
    private ReminderSmallDto _reminder;

    public int getId() {
        return _id;
    }

    public Date getStart() {
        return _start;
    }

    public Date getEnd() {
        return _end;
    }

    public Date getLastRenewalStart() {
        return _lastRenewalStart;
    }

    public boolean getClosed() {
        return _closed;
    }

    public CustomerSmallDto getCustomer() {
        return _customer;
    }

    public MediaMemberSmallDto getMediaMember() {
        return _mediaMember;
    }

    public ReminderSmallDto getReminder() {
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
        this._closed = closed;
    }

    public void setCustomer(CustomerSmallDto customer) {
        _customer = customer;
    }

    public void setMediaMember(MediaMemberSmallDto mediaMember) {
        _mediaMember = mediaMember;
    }

    public void setReminder(ReminderSmallDto reminder) {
        _reminder = reminder;
    }


    // additional getter and setters

    public String getMediaMemberFullIndex() {
        return getMediaMember().getFullIndex();
    }
}