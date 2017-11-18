package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.StaffProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.StaffEntity;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Staff;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class StaffFacade extends BaseDatabaseFacade<Staff, StaffProperty> {
    private static final Type type = new TypeToken<List<Staff>>() {}.getType();

    public StaffFacade() {
        super();
    }

    public StaffFacade(EntityManager session) {
        super(session);
    }

    protected StaffEntity getEntityById(int id, boolean includeInactive) {
        EntityManager session = getCurrentSession();
        String includeInactiveQuery = includeInactive ? "" : " and accountByAccountId.active = true";
        Query query = session.createQuery("from StaffEntity where id = :id" + includeInactiveQuery);
        query.setParameter("id", id);

        return getFirstOrDefault(query);
    }

    @Override
    public Staff getById(int id) {
        StaffEntity entity = getEntityById(id, false);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Staff.class);
        }

        return null;
    }

    @Override
    public List<Staff> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from StaffEntity");
        List<StaffEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(StaffProperty property) {
        switch (property) {
            case ID:
                return "id";
            case ACCOUNT:
                return "accountByAccountId.userName";
            case ADDRESS:
                return "address";
            case BIRTH_DATE:
                return "birthDate";
            case EMAIL:
                return "email";
            case FIRST_NAME:
                return "firstName";
            case LAST_NAME:
                return "lastName";
        }

        return null;
    }

    @Override
    public List<Staff> filter(FilterConnector<StaffProperty, StaffProperty> filterConnector) {
        Query query = getByFilter("from StaffEntity where", getCurrentSession(), filterConnector);
        List<StaffEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(Staff value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        StaffEntity entity = mapper.map(value, StaffEntity.class);

        AccountFacade facade = new AccountFacade(session);
        entity.setAccountId(facade.add(value.getAccount(), TransactionType.MANUAL_COMMIT));

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
        StaffEntity entity = getEntityById(id, false);

        /*Query query = session.createQuery("delete StaffEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();*/

        AccountFacade facade = new AccountFacade(session);
        facade.delete(entity.getAccountId(), TransactionType.MANUAL_COMMIT);

        return StoreHelper.storeEntities(session, transactionType);
    }
}