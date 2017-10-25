import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.AccountRoleFacade;
import team2.database_wrapper.facade.StaffFacade;
import team2.domain.entities.Account;
import team2.domain.entities.AccountRole;
import team2.domain.entities.Staff;

import javax.naming.NamingException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class StaffFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Staff testById(int id) {
        StaffFacade facade = new StaffFacade();
        Staff result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        StaffFacade facade = new StaffFacade();
        List<Staff> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        StaffFacade facade = new StaffFacade();
        Staff value = new Staff();
        value.setFirstName("Max Test");
        value.setLastName("Mustermann Test");
        value.setEmail("max.mustermann@test.at");
        value.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));
        value.setAddress("Addresse Test");

        AccountRoleFacade prevPrevFacade = new AccountRoleFacade(facade.getCurrentSession());
        AccountRole prevPrevValue = new AccountRole();
        prevPrevValue.setKey("Account Role Key Test");
        prevPrevValue.setRoleName("Account Role Name Test");
        prevPrevValue.setId(prevPrevFacade.add(prevPrevValue, TransactionType.MANUAL_COMMIT));

        Account prevValue = new Account();
        prevValue.setUserName("Max Mustermann");
        prevValue.setPassword("Passwort Test");
        prevValue.setAccountRole(prevPrevValue);

        value.setAccount(prevValue);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);

        return id;
    }

    private void testUpdate(int id) {
        StaffFacade facade = new StaffFacade();
        Staff value = facade.getById(id);
        value.setFirstName("Max Test 2");
        value.getAccount().setPassword("Passwort Test 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        StaffFacade facade = new StaffFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
    }
}