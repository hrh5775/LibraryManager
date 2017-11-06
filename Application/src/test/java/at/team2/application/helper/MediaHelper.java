package at.team2.application.helper;

import at.team2.domain.entities.CreatorPerson;
import at.team2.domain.entities.Media;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class MediaHelper {
    public static Media getMedia() {
        Media entity = new Media();
        entity.setId(2);
        entity.setGenre(GenreHelper.getGenre());

        List<CreatorPerson> list = new LinkedList<>();
        list.add(CreatorPersonHelper.getCreatorPerson());
        list.add(CreatorPersonHelper.getCreatorPerson2());

        entity.setCreatorPersons(list);
        entity.setAvailable(true);
        entity.setBaseIndex("xyz5215");
        entity.setComment("bla comment");
        entity.setCover(new byte[]{5, 7});

        entity.setPublishedDate(new Date(Calendar.getInstance().getTime().getTime()));
        entity.setDescription("Des");
        entity.setTitle("titl");

        entity.setMediaType(MediaTypeHelper.getMediaType());
        entity.setPublisher(PublisherHelper.getPublisher());
        entity.setStandardMediaId("lkajdklfjaklsdf21asdf5as4df21");

        return entity;
    }
}
