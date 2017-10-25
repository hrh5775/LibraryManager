package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.AccountRoleEntity;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.AccountRole;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class AccountRoleFacade extends BaseDatabaseFacade<AccountRole> {
    private List<AccountRole> typeList = new LinkedList<>();

    public AccountRoleFacade() {
        super();
    }

    public AccountRoleFacade(EntityManager session) {
        super(session);
    }

    @Override
    public AccountRole getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from AccountRoleEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<AccountRoleEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            AccountRoleEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, AccountRole.class);
        }

        return null;
    }

    @Override
    public List<AccountRole> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from AccountRoleEntity ");
        List<AccountRoleEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, typeList.getClass());
    }

    @Override
    public int add(AccountRole value) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        ModelMapper mapper = MapperHelper.getMapper();
        AccountRoleEntity entity = mapper.map(value, AccountRoleEntity.class);

        session.persist(entity);
        session.flush();
        StoreHelper.storeEntities(session);

        return entity.getId();
    }

    @Override
    public int update(AccountRole value) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        ModelMapper mapper = MapperHelper.getMapper();
        AccountRoleEntity entity = mapper.map(value, AccountRoleEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session);

        return entity.getId();
    }

    @Override
    public boolean delete(int id) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete AccountRoleEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session);
    }
}