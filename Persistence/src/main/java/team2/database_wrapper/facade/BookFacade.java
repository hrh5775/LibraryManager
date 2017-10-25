package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.BookMetaEntity;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class BookFacade extends BaseDatabaseFacade<Book> {
    public BookFacade() {
        super();
    }

    public BookFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Book getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from BookMetaEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<BookMetaEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            BookMetaEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Book.class);
        }

        return null;
    }

    @Override
    public List<Book> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from BookMetaEntity");
        List<BookMetaEntity> entities = query.getResultList();

        ModelMapper mapper = MapperHelper.getMapper();
        List<Book> result = new LinkedList<>();

        return mapper.map(entities, result.getClass());
    }

    @Override
    public int add(Book value) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        ModelMapper mapper = MapperHelper.getMapper();
        BookMetaEntity entity = mapper.map(value, BookMetaEntity.class);

        session.persist(entity);
        session.flush();
        StoreHelper.storeEntities(session);

        return entity.getId();
    }

    @Override
    public int update(Book value) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        ModelMapper mapper = MapperHelper.getMapper();
        BookMetaEntity entity = mapper.map(value, BookMetaEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session);

        return entity.getId();
    }

    @Override
    public boolean delete(int id) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete BookMetaEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session);
    }
}