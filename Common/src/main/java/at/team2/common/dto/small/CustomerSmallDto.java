package at.team2.common.dto.small;

public class CustomerSmallDto
{
    private int _id;
    private String _firstName;
    private String _lastName;
    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String get_firstName()
    {
        return _firstName;
    }

    public void set_firstName(String _firstName)
    {
        this._firstName = _firstName;
    }

    public String get_lastName()
    {
        return _lastName;
    }

    public void set_lastName(String _lastName)
    {
        this._lastName = _lastName;
    }
}
