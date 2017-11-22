package at.team2.domain.enums.properties;

import at.team2.domain.interfaces.DomainEntityProperty;

public enum MediaMemberProperty implements DomainEntityProperty {
    ID,
    MEDIA,
    EXTENDED_INDEX,

    // additional properties
    FULL_INDEX,
    MEDIA__ID
}