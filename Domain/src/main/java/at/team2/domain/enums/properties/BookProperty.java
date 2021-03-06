package at.team2.domain.enums.properties;

import at.team2.domain.interfaces.DomainEntityProperty;

public enum BookProperty implements DomainEntityProperty {
    ID,
    EDITION,
    MEDIA,

    // additional properties for table joins
    MEDIA__ID,
    MEDIA__STANDARD_MEDIA_ID,
    MEDIA__TITLE,
    MEDIA__DESCRIPTION,
    MEDIA__PUBLISHER,
    MEDIA__PUBLISHER_TYPE,
    MEDIA__CREATOR_PERSON,
    MEDIA__CREATOR_TYPE,
    MEDIA__GENRE
}