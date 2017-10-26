package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.DvdMetaEntity;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.Dvd;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class DvdFacade extends BaseDatabaseFacade<Dvd> {
    private List<Dvd> listType = new LinkedList<>();

    public DvdFacade() {
        super();
    }

    public DvdFacade(EntityManager session) {
        super(session);
    }

    private DvdMetaEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from DvdMetaEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<DvdMetaEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
    }

    @Override
    public Dvd getById(int id) {
        DvdMetaEntity entity = getEntityById(id);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Dvd.class);
        }

        return null;
    }

    @Override
    public List<Dvd> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from DvdMetaEntity");
        List<DvdMetaEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, listType.getClass());
    }

    @Override
    public int add(Dvd value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        DvdMetaEntity entity = mapper.map(value, DvdMetaEntity.class);

        MediaFacade facade = new MediaFacade(session);
        entity.setMediaId(facade.add(value.getMedia(), TransactionType.MANUAL_COMMIT));

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Dvd value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        DvdMetaEntity entity = mapper.map(value, DvdMetaEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        DvdMetaEntity entity = getEntityById(id);

        Query query = session.createQuery("delete DvdMetaEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        MediaFacade facade = new MediaFacade(session);
        facade.delete(entity.getMediaId(), TransactionType.MANUAL_COMMIT);

        return StoreHelper.storeEntities(session, transactionType);
    }
}