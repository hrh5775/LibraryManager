package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.CustomerEntity;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Customer;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class CustomerFacade extends BaseDatabaseFacade<Customer> {
    private static final Type type = new TypeToken<List<Customer>>() {}.getType();

    public CustomerFacade() {
        super();
    }

    public CustomerFacade(EntityManager session) {
        super(session);
    }

    private CustomerEntity getEntityById(int id, boolean includeInactive) {
        EntityManager session = getCurrentSession();
        String includeInactiveQuery = includeInactive ? "" : " and accountByAccountId.active >= 1";
        Query query = session.createQuery("from CustomerEntity where id = :id" + includeInactiveQuery);
        query.setParameter("id", id);

        return getFirstOrDefault(query);
    }

    @Override
    public Customer getById(int id) {
        CustomerEntity entity = getEntityById(id, false);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Customer.class);
        }

        return null;
    }

    @Override
    public List<Customer> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from CustomerEntity ");
        List<CustomerEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(Customer value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        CustomerEntity entity = mapper.map(value, CustomerEntity.class);

        AccountFacade facade = new AccountFacade(session);
        entity.setAccountId(facade.add(value.getAccount(), TransactionType.MANUAL_COMMIT));

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Customer value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        CustomerEntity entity = mapper.map(value, CustomerEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        CustomerEntity entity = getEntityById(id, false);

        /*Query query = session.createQuery("delete CustomerEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();*/

        AccountFacade facade = new AccountFacade(session);
        facade.delete(entity.getAccountId(), TransactionType.MANUAL_COMMIT);

        return StoreHelper.storeEntities(session, transactionType);
    }
}