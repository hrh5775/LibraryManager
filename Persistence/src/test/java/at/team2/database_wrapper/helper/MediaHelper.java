package at.team2.database_wrapper.helper;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.*;
import at.team2.domain.entities.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MediaHelper {
    public static Genre createGenre() {
        GenreFacade facade = new GenreFacade();
        Genre value = new Genre();
        value.setName("Test Krimi");
        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));

        return value;
    }

    public static MediaType createMediaType() {
        LoanConditionFacade prevFacade = new LoanConditionFacade();
        LoanCondition prevValue = new LoanCondition();
        prevValue.setLoanTerm(2);
        prevValue.setExtension(1);
        int prevId = prevFacade.add(prevValue, TransactionType.AUTO_COMMIT);
        prevValue.setId(prevId);

        MediaTypeFacade facade = new MediaTypeFacade();
        MediaType value = new MediaType();
        value.setName("Media Type");
        value.setLoanCondition(prevValue);
        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));

        return value;
    }

    public static Publisher createPublisher() {
        PublisherFacade facade = new PublisherFacade();
        Publisher value = new Publisher();
        value.setName("Publisher Test" + (new Random()).nextDouble());
        value.setAddress("Address");

        PublisherType prevValue = new PublisherType();
        prevValue.setTypeName("Publisher Type Name" + (new Random()).nextDouble());

        value.setPublisherType(prevValue);
        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));

        return value;
    }

    public static List<CreatorPerson> createCreators() {
        List<CreatorPerson> list = new LinkedList<>();

        CreatorPersonFacade facade = new CreatorPersonFacade();
        CreatorPerson value = new CreatorPerson();
        value.setFirstName("Max Test");
        value.setLastName("Mustermann");
        value.setAcademicTitle("");
        value.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

        CreatorType prevValue = new CreatorType();
        prevValue.setTypeName("Creator Person Type Name");

        value.setCreatorType(prevValue);
        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));

        list.add(value);

        return list;
    }

    public static Media createMedia(MediaFacade facade) {
        Media value = new Media();
        value.setAvailable(true);
        value.setBaseIndex("base index Test");
        value.setComment("Test");
        value.setCover(new byte[1]);
        value.setPublishedDate(new Date(Calendar.getInstance().getTime().getTime()));
        value.setTitle("Title Test");
        value.setStandardMediaId("standard media id" + (new Random()).nextDouble());
        value.setGenre(MediaHelper.createGenre());
        value.setMediaType(MediaHelper.createMediaType());
        value.setPublisher(MediaHelper.createPublisher());
        value.setCreatorPersons(MediaHelper.createCreators());
        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));

        return value;
    }
}