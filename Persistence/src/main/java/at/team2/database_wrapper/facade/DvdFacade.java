package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.DvdProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.DvdMetaEntity;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Dvd;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class DvdFacade extends BaseDatabaseFacade<Dvd, DvdProperty> {
    private static final Type type = new TypeToken<List<Dvd>>() {}.getType();

    public DvdFacade() {
        super();
    }

    public DvdFacade(EntityManager session) {
        super(session);
    }

    private DvdMetaEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from DvdMetaEntity where id = :id");
        query.setParameter("id", id);

        return getFirstOrDefault(query);
    }

    @Override
    public Dvd getById(int id) {
        DvdMetaEntity entity = getEntityById(id);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            Dvd dvd = mapper.map(entity, Dvd.class);
            dvd.setMedia(MapperHelper.map(entity.getMediaByMediaId()));
            return dvd;
        }

        return null;
    }

    @Override
    public List<Dvd> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from DvdMetaEntity");
        List<DvdMetaEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(DvdProperty property) {
        switch (property) {
            case ID:
                return "id";
            case MEDIA:
                return "mediaByMediaId.title";
            case PLAYING_TIME:
                return "playingTime";
            case MEDIA__TITLE:
                return "mediaByMediaId.title";
            case MEDIA__PUBLISHER:
                return "mediaByMediaId.publisherByPublisherId.name";
            case MEDIA__DESCRIPTION:
                return "mediaByMediaId.description";
            case MEDIA__CREATOR_TYPE:
                return "CreatorPersonEntity.creatorTypeByCreatorTypeId.typeName";
            case MEDIA__PUBLISHER_TYPE:
                return "mediaByMediaId.publisherByPublisherId.publisherTypeByPublisherTypeId.typeName";
            case MEDIA__STANDARD_MEDIA_ID:
                return "mediaByMediaId.standardMediaId";
            case MEDIA__CREATOR_PERSON_LAST_NAME:
                return "CreatorPersonEntity.firstName";
            case MEDIA__CREATOR_PERSON_FIRST_NAME:
                return "CreatorPersonEntity.lastName";
            case MEDIA__GENRE:
                return "mediaByMediaId.genreByGenreId.name";
        }

        return null;
    }

    @Override
    public List<Dvd> filter(FilterConnector<DvdProperty, DvdProperty> filterConnector) {
        Query query = getByFilter("from DvdMetaEntity where", getCurrentSession(), filterConnector);
        List<DvdMetaEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(Dvd value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        DvdMetaEntity entity = mapper.map(value, DvdMetaEntity.class);

        MediaFacade facade = new MediaFacade(session);
        entity.setMediaId(facade.add(value.getMedia(), TransactionType.MANUAL_COMMIT));

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Dvd value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        DvdMetaEntity entity = mapper.map(value, DvdMetaEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        DvdMetaEntity entity = getEntityById(id);

        Query query = session.createQuery("delete DvdMetaEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        MediaFacade facade = new MediaFacade(session);
        facade.delete(entity.getMediaId(), TransactionType.MANUAL_COMMIT);

        return StoreHelper.storeEntities(session, transactionType);
    }
}