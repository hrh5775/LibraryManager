import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.LoanConditionFacade;
import team2.database_wrapper.facade.MediaTypeFacade;
import team2.domain.entities.LoanCondition;
import team2.domain.entities.MediaType;

import javax.naming.NamingException;
import java.util.List;

public class MediaTypeFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private MediaType testById(int id) {
        MediaTypeFacade facade = new MediaTypeFacade();
        MediaType result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        MediaTypeFacade facade = new MediaTypeFacade();
        List<MediaType> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        LoanConditionFacade prevFacade = new LoanConditionFacade();
        LoanCondition prevValue = new LoanCondition();
        prevValue.setLoanTerm(2);
        prevValue.setExtension(1);
        int prevId = prevFacade.add(prevValue, TransactionType.AUTO_COMMIT);
        prevValue.setId(prevId);

        MediaTypeFacade facade = new MediaTypeFacade();
        MediaType value = new MediaType();
        value.setName("Media Type");
        value.setLoanCondition(prevValue);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);

        return id;
    }

    private void testUpdate(int id) {
        LoanConditionFacade prevFacade = new LoanConditionFacade();
        LoanCondition prevValue = new LoanCondition();
        prevValue.setLoanTerm(2);
        prevValue.setExtension(1);
        int prevId = prevFacade.add(prevValue, TransactionType.AUTO_COMMIT);
        prevValue.setId(prevId);

        MediaTypeFacade facade = new MediaTypeFacade();
        MediaType value = facade.getById(id);
        value.setName("Media Type 2");
        value.setLoanCondition(prevValue);
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        MediaTypeFacade facade = new MediaTypeFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
    }
}