package at.team2.database_wrapper.common;

import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.domain.interfaces.DomainEntityProperty;

public class FilterItem<D extends DomainEntityProperty> {
    private Object _parameter;
    private D _property;
    private MatchType _matchType;
    private CaseType _caseType;

    public FilterItem(Object parameter, D property, MatchType matchType, CaseType caseType) {
        _parameter = parameter;
        _property = property;
        _matchType = matchType;
        _caseType = caseType;
    }

    public Object getParameter() {
        return _parameter;
    }

    public D getProperty() {
        return _property;
    }

    public MatchType getMatchType() {
        return _matchType;
    }

    public CaseType getCaseType() {
        return _caseType;
    }
}