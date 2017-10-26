import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.CreatorTypeFacade;
import team2.domain.entities.CreatorType;

import javax.naming.NamingException;
import java.util.List;

public class CreatorTypeFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private CreatorType testById(int id) {
        CreatorTypeFacade facade = new CreatorTypeFacade();
        CreatorType result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        CreatorTypeFacade facade = new CreatorTypeFacade();
        List<CreatorType> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        CreatorTypeFacade facade = new CreatorTypeFacade();
        CreatorType value = new CreatorType();
        value.setTypeName("Creator Type Test");
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        CreatorTypeFacade facade = new CreatorTypeFacade();
        CreatorType value = facade.getById(id);
        value.setTypeName("Creator Type Test 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        CreatorTypeFacade facade = new CreatorTypeFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}