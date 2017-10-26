package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import org.junit.Assert;
import org.junit.Test;
import at.team2.domain.entities.Reminder;

import javax.naming.NamingException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class ReminderFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private Reminder testById(int id) {
        ReminderFacade facade = new ReminderFacade();
        Reminder result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        ReminderFacade facade = new ReminderFacade();
        List<Reminder> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        ReminderFacade facade = new ReminderFacade();
        Reminder value = new Reminder();
        value.setReminderDate(new Date(Calendar.getInstance().getTime().getTime()));
        value.setReminderCount(0);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        ReminderFacade facade = new ReminderFacade();
        Reminder value = facade.getById(id);
        value.setReminderCount(1);
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        ReminderFacade facade = new ReminderFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}