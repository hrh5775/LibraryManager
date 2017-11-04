package at.team2.database_wrapper.common;

public class HibernateParameter {
    private String _preValue;
    private String _postValue;
    private String _identifier;
    private Object _value;

    public HibernateParameter(String identifier, Object value, String preValue, String postValue) {
        _preValue = preValue;
        _postValue = postValue;
        _identifier = identifier;
        _value = value;
    }

    public String getPreValue() {
        return _preValue;
    }

    public String getPostValue() {
        return _postValue;
    }

    public String getIdentifier() {
        return _identifier;
    }

    public Object getValue() {
        return _value;
    }
}