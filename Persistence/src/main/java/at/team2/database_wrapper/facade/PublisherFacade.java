package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterItem;
import at.team2.database_wrapper.entities.PublisherEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.PublisherProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Publisher;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class PublisherFacade extends BaseDatabaseFacade<Publisher, PublisherProperty> {
    private static final Type type = new TypeToken<List<Publisher>>() {}.getType();

    public PublisherFacade() {
        super();
    }

    public PublisherFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Publisher getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from PublisherEntity where id = :id");
        query.setParameter("id", id);
        PublisherEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Publisher.class);
        }

        return null;
    }

    @Override
    public List<Publisher> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from PublisherEntity");
        List<PublisherEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(PublisherProperty property) {
        switch (property) {
            case ID:
                return "id";
            case ADDRESS:
                return "address";
            case NAME:
                return "name";
            case PUBLISHER_TYPE:
                return "publisherTypeByPublisherTypeId.typeName";
        }

        return null;
    }

    @Override
    public List<Publisher> filter(List<FilterItem<PublisherProperty>> filterItems) {
        // @todo: implement
        return null;
    }

    @Override
    public int add(Publisher value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        PublisherEntity entity = mapper.map(value, PublisherEntity.class);

        createPublisherType(entity, value, session);
        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Publisher value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        PublisherEntity entity = mapper.map(value, PublisherEntity.class);

        // the specific publisher type name, etc. can't be changed in this method, because this would affect all other
        // publishers with the same publisher type
        createPublisherType(entity, value, session);
        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    public void createPublisherType(PublisherEntity entity, Publisher value, EntityManager session) {
        if(entity.getPublisherTypeId() <= 0) {
            PublisherTypeFacade publisherTypeFacade = new PublisherTypeFacade(session);
            entity.setPublisherTypeId(publisherTypeFacade.add(value.getPublisherType(), TransactionType.MANUAL_COMMIT));
        }
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete PublisherEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}