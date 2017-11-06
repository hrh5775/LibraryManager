package at.team2.common.dto.small;

import java.sql.Date;

import at.team2.common.interfaces.BaseDtoEntity;

public class LoanSmallDto extends BaseDtoEntity {
    private int _id;
    private Date _start;
    private Date _lastRenewalStart;
    private Date _end;
    private boolean _closed;
    private int _customerId;
    private int _mediaMemberid;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public Date getStart() {
        return _start;
    }

    public void setStart(Date start) {
        _start = start;
    }

    public Date getLastRenewalStart() {
        return _lastRenewalStart;
    }

    public void setLastRenewalStart(Date lastRenewalStart) {
        _lastRenewalStart = lastRenewalStart;
    }

    public Date getEnd() {
        return _end;
    }

    public void setEnd(Date end) {
        _end = end;
    }

    public boolean isClosed() {
        return _closed;
    }

    public void setClosed(boolean closed) {
        _closed = closed;
    }

    public int getCustomerid() {
        return _customerId;
    }

    public void setCustomerid(int customerid) {
        _customerId = customerid;
    }

    public int getMediaMemberid() {
        return _mediaMemberid;
    }

    public void setMediaMemberid(int mediaMemberid) {
        _mediaMemberid = mediaMemberid;
    }
}