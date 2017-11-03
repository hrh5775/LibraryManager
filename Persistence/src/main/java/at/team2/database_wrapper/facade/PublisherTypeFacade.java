package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterItem;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.PublisherTypeProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.PublisherTypeEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.PublisherType;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class PublisherTypeFacade extends BaseDatabaseFacade<PublisherType, PublisherTypeProperty> {
    private static final Type type = new TypeToken<List<PublisherType>>() {}.getType();

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
        PublisherTypeEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, PublisherType.class);
        }

        return null;
    }

    @Override
    public List<PublisherType> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from PublisherTypeEntity");
        List<PublisherTypeEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(PublisherTypeProperty property) {
        switch (property) {
            case ID:
                return "id";
            case TYPE_NAME:
                return "typeName";
        }

        return null;
    }

    @Override
    public List<PublisherType> filter(List<FilterItem<PublisherTypeProperty>> filterItems) {
        // @todo: implement
        return null;
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