import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.CustomerFacade;
import team2.domain.entities.Account;
import team2.domain.entities.AccountRole;
import team2.domain.entities.Customer;

import javax.naming.NamingException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class CustomerFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Customer testById(int id) {
        CustomerFacade facade = new CustomerFacade();
        Customer result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        CustomerFacade facade = new CustomerFacade();
        List<Customer> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        CustomerFacade facade = new CustomerFacade();
        Customer value = new Customer();
        value.setFirstName("Max Test");
        value.setLastName("Mustermann Test");
        value.setEmail("max.mustermann@test.at");
        value.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));
        value.setAddress("Addresse Test");

        AccountRole prevPrevValue = new AccountRole();
        prevPrevValue.setKey("Customer Account Role Key Test" + (new Random()).nextInt());
        prevPrevValue.setRoleName("Account Role Name Test");

        Account prevValue = new Account();
        prevValue.setUserName("Customer Max Test");
        prevValue.setPassword("Passwort Test");
        prevValue.setAccountRole(prevPrevValue);

        value.setAccount(prevValue);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        CustomerFacade facade = new CustomerFacade();
        Customer value = facade.getById(id);
        value.setFirstName("Max Test 2");
        value.getAccount().setPassword("Passwort Test 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        CustomerFacade facade = new CustomerFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}