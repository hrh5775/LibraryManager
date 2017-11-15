package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.CreatorPersonProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.CreatorPersonEntity;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.CreatorPerson;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class CreatorPersonFacade extends BaseDatabaseFacade<CreatorPerson, CreatorPersonProperty> {
    private static final Type type = new TypeToken<List<CreatorPerson>>() {}.getType();

    public CreatorPersonFacade() {
        super();
    }

    public CreatorPersonFacade(EntityManager session) {
        super(session);
    }

    protected CreatorPersonEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from CreatorPersonEntity where id = :id");
        query.setParameter("id", id);

        return getFirstOrDefault(query);
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
        Query query = session.createQuery("from CreatorPersonEntity");
        List<CreatorPersonEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(CreatorPersonProperty property) {
        switch (property) {
            case ID:
                return "id";
            case ACADEMIC_TITLE:
                return "academicTitle";
            case BIRTH_DATE:
                return "birthDate";
            case CREATOR_TYPE:
                return "creatorTypeByCreatorTypeId.typeName";
            case FIRST_NAME:
                return "firstName";
            case LAST_NAME:
                return "lastName";
        }

        return null;
    }

    @Override
    public List<CreatorPerson> filter(FilterConnector<CreatorPersonProperty, CreatorPersonProperty> filterConnector) {
        Query query = getByFilter("from CreatorPersonEntity where", getCurrentSession(), filterConnector);
        List<CreatorPersonEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
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