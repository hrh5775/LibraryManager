package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.BookMetaEntity;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class BookFacade extends BaseDatabaseFacade<Book> {
    private List<Book> listType = new LinkedList<>();

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

        return mapper.map(entities, listType.getClass());
    }

    @Override
    public int add(Book value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        BookMetaEntity entity = mapper.map(value, BookMetaEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Book value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        BookMetaEntity entity = mapper.map(value, BookMetaEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete BookMetaEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}