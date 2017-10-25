import org.junit.Assert;
import org.junit.Test;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.facade.LoanConditionFacade;
import team2.domain.entities.LoanCondition;

import javax.naming.NamingException;
import java.util.List;

public class LoanConditionFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private LoanCondition testById(int id) {
        LoanConditionFacade facade = new LoanConditionFacade();
        LoanCondition result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        LoanConditionFacade facade = new LoanConditionFacade();
        List<LoanCondition> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        LoanConditionFacade facade = new LoanConditionFacade();
        LoanCondition value = new LoanCondition();
        value.setLoanTerm(0);
        value.setExtension(0);
        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);

        return id;
    }

    private void testUpdate(int id) {
        LoanConditionFacade facade = new LoanConditionFacade();
        LoanCondition value = facade.getById(id);
        value.setLoanTerm(2);
        value.setExtension(1);
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        LoanConditionFacade facade = new LoanConditionFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
    }
}