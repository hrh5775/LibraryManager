package at.team2.application.helper;

import at.team2.domain.entities.Publisher;

public class PublisherHelper {
    public static Publisher getPublisher() {
        Publisher entity = new Publisher();
        entity.setId(545);
        entity.setName("lkjaslkd4asdf5as4df21asd5f4asdf145asf");
        entity.setAddress("3838837498dsfksdksdkjds");
        entity.setPublisherType(PublisherTypeHelper.getPublisherType());

        return entity;
    }
}
