package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MediaHelper;
import at.team2.domain.entities.*;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.NamingException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class LoanFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Loan testById(int id) {
        LoanFacade facade = new LoanFacade();
        Loan result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        LoanFacade facade = new LoanFacade();
        List<Loan> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        LoanFacade facade = new LoanFacade();
        Loan value = new Loan();
        value.setStart(new Date(Calendar.getInstance().getTime().getTime()));
        value.setEnd(new Date(Calendar.getInstance().getTime().getTime()));
        value.setLastRenewalStart(new Date(Calendar.getInstance().getTime().getTime()));

        value.setCustomer(createCustomer());
        value.setMediaMember(createMediaMember());
        value.setReminder(createReminder());

        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private Customer createCustomer() {
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
        return facade.getById(id);
    }

    public MediaMember createMediaMember() {
        MediaMemberFacade facade = new MediaMemberFacade();
        MediaMember value = new MediaMember();
        value.setExtendedIndex("Extension Index Test");
        value.setMediaId(MediaHelper.createMedia(new MediaFacade()));

        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));
        return value;
    }

    public Reminder createReminder() {
        ReminderFacade facade = new ReminderFacade();
        Reminder value = new Reminder();
        value.setReminderDate(new Date(Calendar.getInstance().getTime().getTime()));
        value.setReminderCount(0);
        value.setId(facade.add(value, TransactionType.AUTO_COMMIT));

        return value;
    }

    private void testUpdate(int id) {
        LoanFacade facade = new LoanFacade();
        Loan value = facade.getById(id);
        value.setEnd(new Date(Calendar.getInstance().getTime().getTime()));
        value.setClosed(true);
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        LoanFacade facade = new LoanFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}