import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.AccountRoleFacade;
import team2.domain.entities.AccountRole;

import javax.naming.NamingException;
import java.util.List;

public class AccountRoleFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private AccountRole testById(int id) {
        AccountRoleFacade facade = new AccountRoleFacade();
        AccountRole result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        AccountRoleFacade facade = new AccountRoleFacade();
        List<AccountRole> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        AccountRoleFacade facade = new AccountRoleFacade();
        AccountRole value = new AccountRole();
        value.setKey("TEST_KEY");
        value.setRoleName("Test Role");
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        AccountRoleFacade facade = new AccountRoleFacade();
        AccountRole value = facade.getById(id);
        value.setKey("TEST_KEY_2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        AccountRoleFacade facade = new AccountRoleFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}