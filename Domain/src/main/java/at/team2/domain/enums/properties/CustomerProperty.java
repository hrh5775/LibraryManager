package at.team2.domain.enums.properties;

import at.team2.domain.interfaces.DomainEntityProperty;

public enum CustomerProperty implements DomainEntityProperty {
    ID,
    FIRST_NAME,
    LAST_NAME,
    BIRTH_DATE,
    ADDRESS,
    EMAIL,
    PHONE_NUMBER,
    BANK_ACCOUNT_NUMBER,
    DATE_OF_EXPIRY,
    ACCOUNT,

    // additional properties
    FULL_NAME
}