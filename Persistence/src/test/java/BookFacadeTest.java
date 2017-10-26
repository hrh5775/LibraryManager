import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.*;
import team2.domain.entities.*;

import javax.naming.NamingException;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BookFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Book testById(int id) {
        BookFacade facade = new BookFacade();
        Book result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        BookFacade facade = new BookFacade();
        List<Book> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        BookFacade facade = new BookFacade();
        Book value = new Book();
        value.setEdition(1);

        Media prevValue = new Media();
        prevValue.setAvailable(true);
        prevValue.setBaseIndex("base index Test");
        prevValue.setComment("Test");
        prevValue.setCover(new byte[1]);
        prevValue.setPublishedDate(new Date(Calendar.getInstance().getTime().getTime()));
        prevValue.setTitle("Title Test");
        prevValue.setStandardMediaId("standard media id");

        prevValue.setGenre(createGenre());
        prevValue.setMediaType(createMediaType());
        prevValue.setPublisher(createPublisher());
        prevValue.setCreatorPersons(createCreators());

        value.setMedia(prevValue);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private Genre createGenre() {
        GenreFacade facade = new GenreFacade();
        Genre value = new Genre();
        value.setName("Test Krimi");
        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));

        return value;
    }

    private MediaType createMediaType() {
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

    private Publisher createPublisher() {
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

    private List<CreatorPerson> createCreators() {
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

    private void testUpdate(int id) {
        BookFacade facade = new BookFacade();
        Book value = facade.getById(id);
        value.setEdition(3);
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        BookFacade facade = new BookFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}