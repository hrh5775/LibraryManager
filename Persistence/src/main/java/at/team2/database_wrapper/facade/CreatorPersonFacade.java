package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.CreatorPersonEntity;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.CreatorPerson;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class CreatorPersonFacade extends BaseDatabaseFacade<CreatorPerson> {
    private List<CreatorPerson> typeList = new LinkedList<>();

    public CreatorPersonFacade() {
        super();
    }

    public CreatorPersonFacade(EntityManager session) {
        super(session);
    }

    private CreatorPersonEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from CreatorPersonEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<CreatorPersonEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
    }

    @Override
    public CreatorPerson getById(int id) {
        CreatorPersonEntity entity = getEntityById(id);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, CreatorPerson.class);
        }

        return null;
    }

    @Override
    public List<CreatorPerson> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from CreatorPersonEntity ");
        List<CreatorPersonEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, typeList.getClass());
    }

    @Override
    public int add(CreatorPerson value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        CreatorPersonEntity entity = mapper.map(value, CreatorPersonEntity.class);

        createCreatorPersonType(entity, value, session);
        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(CreatorPerson value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        CreatorPersonEntity entity = mapper.map(value, CreatorPersonEntity.class);

        createCreatorPersonType(entity, value, session);
        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    public void createCreatorPersonType(CreatorPersonEntity entity, CreatorPerson value, EntityManager session) {
        if(entity.getCreatorTypeId() <= 0) {
            CreatorTypeFacade facade = new CreatorTypeFacade(session);
            entity.setCreatorTypeId(facade.add(value.getCreatorType(), TransactionType.MANUAL_COMMIT));
        }
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete CreatorPersonEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}