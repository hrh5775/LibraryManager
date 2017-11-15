package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.BookProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.BookMetaEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Book;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class BookFacade extends BaseDatabaseFacade<Book, BookProperty> {
    private static final Type type = new TypeToken<List<Book>>() {}.getType();

    public BookFacade() {
        super();
    }

    public BookFacade(EntityManager session) {
        super(session);
    }

    protected BookMetaEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from BookMetaEntity where id = :id");
        query.setParameter("id", id);

        return getFirstOrDefault(query);
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

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(BookProperty property) {
        switch (property) {
            case ID:
                return "id";
            case MEDIA:
                return "mediaByMediaId.title";
            case EDITION:
                return "edition";
            case MEDIA__TITLE:
                return "mediaByMediaId.title";
            case MEDIA__PUBLISHER:
                return "mediaByMediaId.publisherByPublisherId.name";
            case MEDIA__DESCRIPTION:
                return "mediaByMediaId.description";
            case MEDIA__CREATOR_TYPE:
                return "(select group_concat(creatorPersonByCreatorPersonId.creatorTypeByCreatorTypeId.typeName) from MediaCreatorPersonEntity where mediaId = book.mediaId)";
            case MEDIA__PUBLISHER_TYPE:
                return "mediaByMediaId.publisherByPublisherId.publisherTypeByPublisherTypeId.typeName";
            case MEDIA__STANDARD_MEDIA_ID:
                return "mediaByMediaId.standardMediaId";
            case MEDIA__CREATOR_PERSON:
                return "(select group_concat(concat(creatorPersonByCreatorPersonId.firstName, ' ', creatorPersonByCreatorPersonId.lastName)) from MediaCreatorPersonEntity where mediaId = book.mediaId)";
            case MEDIA__GENRE:
                return "mediaByMediaId.genreByGenreId.name";
        }

        return null;
    }

    @Override
    public List<Book> filter(FilterConnector<BookProperty, BookProperty> filterConnector) {
        Query query = getByFilter("from BookMetaEntity as book where", getCurrentSession(), filterConnector);
        List<BookMetaEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
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