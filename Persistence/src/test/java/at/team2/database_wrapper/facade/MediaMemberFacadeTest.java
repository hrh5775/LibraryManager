package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MediaHelper;
import at.team2.domain.entities.MediaMember;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.List;

public class MediaMemberFacadeTest {
    @Test
    public void testAll() throws NamingException {
        int id = testAdd();
        testById(id);
        testGetList();
        testUpdate(id);
        testDelete(id);
    }

    private MediaMember testById(int id) {
        MediaMemberFacade facade = new MediaMemberFacade();
        MediaMember result = facade.getById(id);
        Assert.assertNotNull(result);

        return result;
    }

    private void testGetList() {
        MediaMemberFacade facade = new MediaMemberFacade();
        List<MediaMember> list = facade.getList();
        Assert.assertTrue(list.size() > 0);
    }

    private int testAdd() {
        MediaMemberFacade facade = new MediaMemberFacade();
        MediaMember value = new MediaMember();
        value.setExtendedIndex("Extension Index Test");
        value.setMediaId(MediaHelper.createMedia(new MediaFacade()));

        int id = facade.add(value, TransactionType.AUTO_COMMIT);
        Assert.assertTrue(id > 0);
        Assert.assertNotNull(facade.getById(id));

        return id;
    }

    private void testUpdate(int id) {
        MediaMemberFacade facade = new MediaMemberFacade();
        MediaMember value = facade.getById(id);
        value.setExtendedIndex("Extension Index Test 2");
        Assert.assertTrue(facade.update(value, TransactionType.AUTO_COMMIT) > 0);
    }

    private void testDelete(int id) {
        MediaMemberFacade facade = new MediaMemberFacade();
        Assert.assertTrue(facade.delete(id, TransactionType.AUTO_COMMIT));
        Assert.assertNull(facade.getById(id));
    }
}