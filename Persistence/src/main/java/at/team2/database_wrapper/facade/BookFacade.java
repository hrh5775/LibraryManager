package at.team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.BookMetaEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Book;

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

    private BookMetaEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from BookMetaEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<BookMetaEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
    }

    @Override
    public Book getById(int id) {
        BookMetaEntity entity = getEntityById(id);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            Book book = mapper.map(entity, Book.class);
            book.setMedia(MapperHelper.map(entity.getMediaByMediaId()));

            return book;
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

        MediaFacade facade = new MediaFacade(session);
        entity.setMediaId(facade.add(value.getMedia(), TransactionType.MANUAL_COMMIT));

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
        BookMetaEntity entity = getEntityById(id);

        Query query = session.createQuery("delete BookMetaEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        MediaFacade facade = new MediaFacade(session);
        facade.delete(entity.getMediaId(), TransactionType.MANUAL_COMMIT);

        return StoreHelper.storeEntities(session, transactionType);
    }
}