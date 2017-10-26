import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.CreatorPersonFacade;
import team2.domain.entities.CreatorPerson;
import team2.domain.entities.CreatorType;

import javax.naming.NamingException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class CreatorPersonFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private CreatorPerson testById(int id) {
        CreatorPersonFacade facade = new CreatorPersonFacade();
        CreatorPerson result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        CreatorPersonFacade facade = new CreatorPersonFacade();
        List<CreatorPerson> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        CreatorPersonFacade facade = new CreatorPersonFacade();
        CreatorPerson value = new CreatorPerson();
        value.setFirstName("Max Test");
        value.setLastName("Mustermann");
        value.setAcademicTitle("");
        value.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

        CreatorType prevValue = new CreatorType();
        prevValue.setTypeName("Creator Person Type Name");

        value.setCreatorType(prevValue);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        CreatorPersonFacade facade = new CreatorPersonFacade();
        CreatorPerson value = facade.getById(id);
        value.setLastName("Mustermann 2");
        value.getCreatorType().setTypeName("Creator Person Type Name 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        CreatorPersonFacade facade = new CreatorPersonFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}