package at.team2.common.dto.small;


import java.sql.Date;

import at.team2.common.interfaces.BaseDtoEntity;

public class LoanSmallDto extends BaseDtoEntity
{
    private int _id;
    private Date _start;
    private Date _lastRenewalStart;
    private Date _end;
    private boolean _closed;
    private int _customerid;
    private int _mediaMemberid;

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public Date get_start()
    {
        return _start;
    }

    public void set_start(Date _start)
    {
        this._start = _start;
    }

    public Date get_lastRenewalStart()
    {
        return _lastRenewalStart;
    }

    public void set_lastRenewalStart(Date _lastRenewalStart)
    {
        this._lastRenewalStart = _lastRenewalStart;
    }

    public Date get_end()
    {
        return _end;
    }

    public void set_end(Date _end)
    {
        this._end = _end;
    }

    public boolean is_closed()
    {
        return _closed;
    }

    public void set_closed(boolean _closed)
    {
        this._closed = _closed;
    }

    public int get_customerid()
    {
        return _customerid;
    }

    public void set_customerid(int _customerid)
    {
        this._customerid = _customerid;
    }

    public int get_mediaMemberid()
    {
        return _mediaMemberid;
    }

    public void set_mediaMemberid(int _mediaMemberid)
    {
        this._mediaMemberid = _mediaMemberid;
    }

}
