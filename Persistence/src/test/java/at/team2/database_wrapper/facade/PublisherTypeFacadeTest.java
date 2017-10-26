package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import org.junit.Assert;
import org.junit.Test;
import at.team2.domain.entities.PublisherType;

import javax.naming.NamingException;
import java.util.List;

public class PublisherTypeFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private PublisherType testById(int id) {
        PublisherTypeFacade facade = new PublisherTypeFacade();
        PublisherType result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        PublisherTypeFacade facade = new PublisherTypeFacade();
        List<PublisherType> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        PublisherTypeFacade facade = new PublisherTypeFacade();
        PublisherType value = new PublisherType();
        value.setTypeName("Publisher Type Test");
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        PublisherTypeFacade facade = new PublisherTypeFacade();
        PublisherType value = facade.getById(id);
        value.setTypeName("Publisher Type Test 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        PublisherTypeFacade facade = new PublisherTypeFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}