package at.team2.application.helper;

import at.team2.domain.entities.Book;

public class BookHelper {
    public static Book getBook() {
        Book entity = new Book();
        entity.setId(1);
        entity.setEdition(3);
        entity.setMedia(MediaHelper.getMedia());

        return entity;
    }
}
