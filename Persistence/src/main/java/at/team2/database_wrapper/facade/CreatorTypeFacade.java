package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.entities.CreatorTypeEntity;
import at.team2.database_wrapper.enums.TransactionType;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.CreatorType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class CreatorTypeFacade extends BaseDatabaseFacade<CreatorType> {
    private List<CreatorType> typeList = new LinkedList<>();

    public CreatorTypeFacade() {
        super();
    }

    public CreatorTypeFacade(EntityManager session) {
        super(session);
    }

    @Override
    public CreatorType getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from CreatorTypeEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<CreatorTypeEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            CreatorTypeEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, CreatorType.class);
        }

        return null;
    }

    @Override
    public List<CreatorType> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from CreatorTypeEntity ");
        List<CreatorTypeEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, typeList.getClass());
    }

    @Override
    public int add(CreatorType value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        CreatorTypeEntity entity = mapper.map(value, CreatorTypeEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(CreatorType value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        CreatorTypeEntity entity = mapper.map(value, CreatorTypeEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete CreatorTypeEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}