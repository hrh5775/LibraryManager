package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import org.junit.Assert;
import org.junit.Test;
import at.team2.domain.entities.Publisher;
import at.team2.domain.entities.PublisherType;

import javax.naming.NamingException;
import java.util.List;
import java.util.Random;

public class PublisherFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Publisher testById(int id) {
        PublisherFacade facade = new PublisherFacade();
        Publisher result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        PublisherFacade facade = new PublisherFacade();
        List<Publisher> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        PublisherFacade facade = new PublisherFacade();
        Publisher value = new Publisher();
        value.setName("Publisher Test" + (new Random()).nextDouble());
        value.setAddress("Address");

        PublisherType prevValue = new PublisherType();
        prevValue.setTypeName("Publisher Type Name" + (new Random()).nextDouble());

        value.setPublisherType(prevValue);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        PublisherFacade facade = new PublisherFacade();
        Publisher value = facade.getById(id);
        value.setName("Publisher Test 2");
        value.getPublisherType().setTypeName("Publisher Type Name 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        PublisherFacade facade = new PublisherFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}