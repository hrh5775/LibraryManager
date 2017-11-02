package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.MediaTypeEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.MediaType;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class MediaTypeFacade extends BaseDatabaseFacade<MediaType> {
    private static final Type type = new TypeToken<List<MediaType>>() {}.getType();

    public MediaTypeFacade() {
        super();
    }

    public MediaTypeFacade(EntityManager session) {
        super(session);
    }

    @Override
    public MediaType getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaTypeEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        MediaTypeEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, MediaType.class);
        }

        return null;
    }

    @Override
    public List<MediaType> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaTypeEntity ");
        List<MediaTypeEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(MediaType value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        MediaTypeEntity entity = mapper.map(value, MediaTypeEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(MediaType value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        MediaTypeEntity entity = mapper.map(value, MediaTypeEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete MediaTypeEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}