package at.team2.domain.enums.properties;

import at.team2.domain.interfaces.DomainEntityProperty;

public enum  LoanProperty implements DomainEntityProperty {
    ID,
    START,
    LAST_RENEWAL_START,
    END,
    CLOSED,
    CUSTOMER,
    MEDIA_MEMBER,
    REMINDER
}