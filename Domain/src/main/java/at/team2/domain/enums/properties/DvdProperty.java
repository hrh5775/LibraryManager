package at.team2.domain.enums.properties;

import at.team2.domain.interfaces.DomainEntityProperty;

public enum DvdProperty implements DomainEntityProperty {
    ID,
    PLAYING_TIME,
    MEDIA,

    // additional properties for table joins
    MEDIA__STANDARD_MEDIA_ID,
    MEDIA__TITLE,
    MEDIA__DESCRIPTION,
    MEDIA__PUBLISHER,
    MEDIA__PUBLISHER_TYPE,
    MEDIA__CREATOR_PERSON_FIRST_NAME,
    MEDIA__CREATOR_PERSON_LAST_NAME,
    MEDIA__CREATOR_TYPE,
    MEDIA__GENRE
}