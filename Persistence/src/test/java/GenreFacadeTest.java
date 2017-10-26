import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.GenreFacade;
import team2.domain.entities.Genre;

import javax.naming.NamingException;
import java.util.List;

public class GenreFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Genre testById(int id) {
        GenreFacade facade = new GenreFacade();
        Genre result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        GenreFacade facade = new GenreFacade();
        List<Genre> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        GenreFacade facade = new GenreFacade();
        Genre value = new Genre();
        value.setName("Test Krimi");
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        GenreFacade facade = new GenreFacade();
        Genre value = facade.getById(id);
        value.setName("Test Krimi 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        GenreFacade facade = new GenreFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}