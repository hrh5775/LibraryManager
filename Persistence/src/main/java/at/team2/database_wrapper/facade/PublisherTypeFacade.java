package at.team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.PublisherTypeEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.PublisherType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class PublisherTypeFacade extends BaseDatabaseFacade<PublisherType> {
    private static List<PublisherType> listType = new LinkedList<>();

    public PublisherTypeFacade() {
        super();
    }

    public PublisherTypeFacade(EntityManager session) {
        super(session);
    }

    @Override
    public PublisherType getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from PublisherTypeEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<PublisherTypeEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            PublisherTypeEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, PublisherType.class);
        }

        return null;
    }

    @Override
    public List<PublisherType> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from PublisherTypeEntity ");
        List<PublisherTypeEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, listType.getClass());
    }

    @Override
    public int add(PublisherType value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        PublisherTypeEntity entity = mapper.map(value, PublisherTypeEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(PublisherType value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        PublisherTypeEntity entity = mapper.map(value, PublisherTypeEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete PublisherTypeEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}