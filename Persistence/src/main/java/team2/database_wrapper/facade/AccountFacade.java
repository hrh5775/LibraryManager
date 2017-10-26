package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.AccountEntity;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class AccountFacade extends BaseDatabaseFacade<Account> {
    private List<Account> typeList = new LinkedList<>();

    protected AccountFacade() {
        super();
    }

    protected AccountFacade(EntityManager session) {
        super(session);
    }

    private AccountEntity getEntityById(int id, boolean includeInactive) {
        EntityManager session = getCurrentSession();
        String includeInactiveQuery = includeInactive ? "" : " and active >= 1";
        Query query = session.createQuery("from AccountEntity where id = :id" + includeInactiveQuery);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<AccountEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
    }

    @Override
    public Account getById(int id) {
        AccountEntity entity = getEntityById(id, false);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Account.class);
        }

        return null;
    }

    @Override
    public List<Account> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from AccountEntity where active");
        List<AccountEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, typeList.getClass());
    }

    @Override
    public int add(Account value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        AccountEntity entity = mapper.map(value, AccountEntity.class);

        if(entity.getAccountRoleId() <= 0) {
            AccountRoleFacade accountRoleFacade = new AccountRoleFacade(session);
            entity.setAccountRoleId(accountRoleFacade.add(value.getAccountRole(), TransactionType.MANUAL_COMMIT));
        }

        entity.setActive(true);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Account value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        AccountEntity entity = mapper.map(value, AccountEntity.class);

        // Prevent the user from inserting an active flag. Use the delete method instead.
        // In the current state an account can't be reactivated.
        entity.setActive(true);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        /*Query query = session.createQuery("delete AccountEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();*/

        AccountEntity entity = getEntityById(id, false);
        entity.setActive(false);

        return StoreHelper.storeEntities(session, transactionType);
    }
}