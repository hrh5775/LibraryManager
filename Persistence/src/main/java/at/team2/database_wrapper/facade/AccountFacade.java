package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.AccountProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.AccountEntity;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Account;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class AccountFacade extends BaseDatabaseFacade<Account, AccountProperty> {
    private static final Type type = new TypeToken<List<Account>>() {}.getType();

    public AccountFacade() {
        super();
    }

    public AccountFacade(EntityManager session) {
        super(session);
    }

    protected AccountEntity getEntityById(int id, boolean includeInactive) {
        EntityManager session = getCurrentSession();
        String includeInactiveQuery = includeInactive ? "" : " and active = true";
        Query query = session.createQuery("from AccountEntity where id = :id" + includeInactiveQuery);
        query.setParameter("id", id);

        return getFirstOrDefault(query);
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
        Query query = session.createQuery("from AccountEntity where active = true");
        List<AccountEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(AccountProperty property) {
        switch (property) {
            case ID:
                return "id";
            case ACCOUNT_ROLE:
                return "accountRoleByAccountRoleId.roleName";
            case PASSWORT:
                return "password";
            case USER_NAME:
                return "userName";
        }

        return null;
    }

    @Override
    public List<Account> filter(FilterConnector<AccountProperty, AccountProperty> filterConnector) {
        Query query = getByFilter("from AccountEntity where active = true and", getCurrentSession(), filterConnector);
        List<AccountEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
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

        // an account role name can't be changed in this method

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