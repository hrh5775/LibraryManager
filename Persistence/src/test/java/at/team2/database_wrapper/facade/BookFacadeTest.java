package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.domain.entities.Book;
import at.team2.domain.entities.Media;
import org.junit.Assert;
import org.junit.Test;
import at.team2.database_wrapper.helper.MediaHelper;

import javax.naming.NamingException;
import java.sql.Date;
import java.util.Calendar;
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
        prevValue.setStandardMediaId("standard media id" + (new Random()).nextDouble());

        prevValue.setGenre(MediaHelper.createGenre());
        prevValue.setMediaType(MediaHelper.createMediaType());
        prevValue.setPublisher(MediaHelper.createPublisher());
        prevValue.setCreatorPersons(MediaHelper.createCreators());

        value.setMedia(prevValue);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
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