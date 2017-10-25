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

    @Override
    public Account getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from AccountEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<AccountEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            AccountEntity entity = entities.get(0);
            ModelMapper mapper = MapperHelper.getMapper();

            return mapper.map(entity, Account.class);
        }

        return null;
    }

    @Override
    public List<Account> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from AccountEntity ");
        List<AccountEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, typeList.getClass());
    }

    @Override
    public int add(Account value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        AccountEntity entity = mapper.map(value, AccountEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Account value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        AccountEntity entity = mapper.map(value, AccountEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete AccountEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}