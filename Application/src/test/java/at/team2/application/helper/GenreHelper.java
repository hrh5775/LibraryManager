package at.team2.application.helper;

import at.team2.domain.entities.Genre;

public class GenreHelper {
    public static Genre getGenre() {
        Genre entity = new Genre();
        entity.setId(9);
        entity.setName("wtf");

        return entity;
    }
}
