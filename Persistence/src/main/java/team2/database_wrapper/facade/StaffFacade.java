package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.AccountEntity;
import team2.database_wrapper.entities.AccountRoleEntity;
import team2.database_wrapper.entities.StaffEntity;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.Account;
import team2.domain.entities.AccountRole;
import team2.domain.entities.Staff;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class StaffFacade extends BaseDatabaseFacade<Staff> {
    private List<Staff> typeList = new LinkedList<>();

    public StaffFacade() {
        super();
    }

    public StaffFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Staff getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from StaffEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<StaffEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            StaffEntity entity = entities.get(0);
            ModelMapper mapper = MapperHelper.getMapper();

            return mapper.map(entity, Staff.class);
        }

        return null;
    }

    @Override
    public List<Staff> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from StaffEntity ");
        List<StaffEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, typeList.getClass());
    }

    @Override
    public int add(Staff value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        StaffEntity entity = mapper.map(value, StaffEntity.class);

        //AccountFacade facade = new AccountFacade(session);
        //entity.setAccountId(facade.add(value.getAccount(), TransactionType.MANUAL_COMMIT)); // @todo

        Account account = value.getAccount();
        AccountEntity accountEntity = mapper.map(account, AccountEntity.class);

        int accountRoleId = value.getAccount().getAccountRole().getID();

        if(accountRoleId > 0) {
            accountEntity.setAccountRoleId(value.getAccount().getAccountRole().getID());// @todo: accountRole id überprüfen => ansonsten erstellen
        } else {
            AccountRole accountRole = value.getAccount().getAccountRole();
            AccountRoleEntity accountRoleEntity = mapper.map(accountRole, AccountRoleEntity.class);
            accountEntity.setAccountRoleByAccountRoleId(accountRoleEntity);
        }

        entity.setAccountByAccountId(accountEntity);
        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Staff value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        StaffEntity entity = mapper.map(value, StaffEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete StaffEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        //AccountFacade facade = new AccountFacade(session);
        //facade.delete()

        // @todo: delete account
        return StoreHelper.storeEntities(session, transactionType);
    }
}