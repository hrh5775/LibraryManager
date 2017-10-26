import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.ConfigurationFacade;
import team2.domain.entities.Configuration;

import javax.naming.NamingException;
import java.util.List;

public class ConfigurationFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testByKey("IDENTIFIER_TEST");
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Configuration testById(int id) {
        ConfigurationFacade facade = new ConfigurationFacade();
        Configuration result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private Configuration testByKey(String key) {
        ConfigurationFacade facade = new ConfigurationFacade();
        Configuration result = facade.getByKey(key);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        ConfigurationFacade facade = new ConfigurationFacade();
        List<Configuration> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        ConfigurationFacade facade = new ConfigurationFacade();
        Configuration value = new Configuration();
        value.setIdentifier("IDENTIFIER_TEST");
        value.setData("Data Test");
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        ConfigurationFacade facade = new ConfigurationFacade();
        Configuration value = facade.getById(id);
        value.setIdentifier("IDENTIFIER_TEST_2");
        value.setData("Data Test 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        ConfigurationFacade facade = new ConfigurationFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}