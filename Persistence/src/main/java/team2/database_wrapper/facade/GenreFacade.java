package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.GenreEntity;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.Genre;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class GenreFacade extends BaseDatabaseFacade<Genre> {
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
        query.setMaxResults(1);
        List<GenreEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            GenreEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Genre.class);
        }

        return null;
    }

    @Override
    public List<Genre> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from GenreEntity ");
        List<GenreEntity> entities = query.getResultList();

        ModelMapper mapper = MapperHelper.getMapper();
        List<Genre> result = new LinkedList<>();

        return mapper.map(entities, result.getClass());
    }

    @Override
    public int add(Genre value) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        ModelMapper mapper = MapperHelper.getMapper();
        GenreEntity entity = mapper.map(value, GenreEntity.class);

        session.persist(entity);
        session.flush();
        StoreHelper.storeEntities(session);

        return entity.getId();
    }

    @Override
    public int update(Genre value) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        ModelMapper mapper = MapperHelper.getMapper();
        GenreEntity entity = mapper.map(value, GenreEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session);

        return entity.getId();
    }

    @Override
    public boolean delete(int id) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete GenreEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session);
    }
}