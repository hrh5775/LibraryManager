package at.team2.application.helper;

import at.team2.domain.entities.Dvd;

public class DvdHelper {
    public static Dvd getDvd() {
        Dvd entity = new Dvd();
        entity.setId(51455);
        entity.setMedia(MediaHelper.getMedia());
        entity.setPlayingTime(83482302);

        return entity;
    }
}