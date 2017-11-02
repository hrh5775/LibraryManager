package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.entities.ConfigurationEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Configuration;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class ConfigurationFacade extends BaseDatabaseFacade<Configuration> {
    private static final Type type = new TypeToken<List<Configuration>>() {}.getType();

    public ConfigurationFacade() {
        super();
    }

    public ConfigurationFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Configuration getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from ConfigurationEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        ConfigurationEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Configuration.class);
        }

        return null;
    }

    public Configuration getByKey(String key) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from ConfigurationEntity where identifier = :key");
        query.setParameter("key", key);
        query.setMaxResults(1);
        List<ConfigurationEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            ConfigurationEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Configuration.class);
        }

        return null;
    }

    @Override
    public List<Configuration> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from ConfigurationEntity");
        List<ConfigurationEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(Configuration value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        ConfigurationEntity entity = mapper.map(value, ConfigurationEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Configuration value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        ConfigurationEntity entity = mapper.map(value, ConfigurationEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete ConfigurationEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}