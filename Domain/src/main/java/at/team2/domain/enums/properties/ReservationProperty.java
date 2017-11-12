package at.team2.domain.enums.properties;

import at.team2.domain.interfaces.DomainEntityProperty;

public enum ReservationProperty implements DomainEntityProperty {
    ID,
    RESERVATION_DATE,
    INFORMATION_DATE,
    CLOSED,
    CUSTOMER,
    MEDIA,

    // additional property
    CUSTOMER__ID
}