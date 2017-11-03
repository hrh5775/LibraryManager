package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterItem;
import at.team2.database_wrapper.entities.GenreEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.GenreProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Genre;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class GenreFacade extends BaseDatabaseFacade<Genre, GenreProperty> {
    private static final List<Genre> listType = new LinkedList<>();

    public GenreFacade() {
        super();
    }

    public GenreFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Genre getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from GenreEntity where id = :id");
        query.setParameter("id", id);
        GenreEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Genre.class);
        }

        return null;
    }

    @Override
    public List<Genre> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from GenreEntity");
        List<GenreEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, listType.getClass());
    }

    @Override
    protected String getColumnNameForProperty(GenreProperty property) {
        switch (property) {
            case ID:
                return "id";
            case NAME:
                return "name";
        }

        return null;
    }

    @Override
    public List<Genre> filter(List<FilterItem<GenreProperty>> filterItems) {
        // @todo: implement
        return null;
    }

    @Override
    public int add(Genre value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        GenreEntity entity = mapper.map(value, GenreEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Genre value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        GenreEntity entity = mapper.map(value, GenreEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete GenreEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}