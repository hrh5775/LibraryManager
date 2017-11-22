package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.entities.MediaCreatorPersonEntity;
import at.team2.database_wrapper.entities.MediaEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.MediaProperty;
import org.modelmapper.ModelMapper;
import at.team2.domain.entities.CreatorPerson;
import at.team2.domain.entities.Media;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class MediaFacade extends BaseDatabaseFacade<Media, MediaProperty> {
    private static final Type type = new TypeToken<List<Media>>() {}.getType();

    public MediaFacade() {
        super();
    }

    public MediaFacade(EntityManager session) {
        super(session);
    }

    protected MediaEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaEntity where id = :id");
        query.setParameter("id", id);

        return getFirstOrDefault(query);
    }

    @Override
    public Media getById(int id) {
        MediaEntity entity = getEntityById(id);

        if (entity != null) {
            return MapperHelper.map(entity);
        }

        return null;
    }

    @Override
    public List<Media> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaEntity");
        List<MediaEntity> entities = query.getResultList();

        return MapperHelper.map(entities);
    }

    @Override
    protected String getColumnNameForProperty(MediaProperty property) {
        switch (property) {
            case ID:
                return "id";
            case AVAILABLE:
                return "available";
            case BASE_INDEX:
                return "baseIndex";
            case COMMENT:
                return "comment";
            case COVER:
                return "cover";
            case CREATOR_PERSONS:
                return "mediaCreatorPeopleById";
            case GENRE:
                return "genreByGenreId.name";
            case TITLE:
                return "title";
            case PUBLISHER:
                return "publisherByPublisherId.name";
            case MEDIA_TYPE:
                return "mediaTypeByMediaTypeId.name";
            case DESCRIPTION:
                return "description";
            case PUBLISHED_DATE:
                return "publishedDate";
            case STANDARD_MEDIA_ID:
                return "standardMediaId";
        }

        return null;
    }

    @Override
    public List<Media> filter(FilterConnector<MediaProperty, MediaProperty> filterConnector) {
        Query query = getByFilter("from MediaEntity where", getCurrentSession(), filterConnector);
        List<MediaEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(Media value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        MediaEntity entity = mapper.map(value, MediaEntity.class);

        // a reservation will not be added
        entity.setReservationsById(null);
        createEntities(entity, value, session);
        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        createCreatorPersonEntities(entity, value, session);

        return entity.getId();
    }

    @Override
    public int update(Media value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        MediaEntity entity = mapper.map(value, MediaEntity.class);

        // a genre name, publisher name, reservation name can't be changed in this method, because this would affect
        // multiple media
        createEntities(entity, value, session);
        createCreatorPersonEntities(entity, value, session);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    public void createEntities(MediaEntity entity, Media value, EntityManager session) {
        if(entity.getGenreId() <= 0) {
            GenreFacade genreFacade = new GenreFacade(session);
            entity.setGenreId(genreFacade.add(value.getGenre(), TransactionType.MANUAL_COMMIT));
        }

        if(entity.getPublisherId() <= 0) {
            PublisherFacade publisherFacade = new PublisherFacade(session);
            entity.setPublisherId(publisherFacade.add(value.getPublisher(), TransactionType.MANUAL_COMMIT));
        }

        if(entity.getMediaTypeId() <= 0) {
            MediaTypeFacade mediaTypeFacade = new MediaTypeFacade(session);
            entity.setMediaTypeId(mediaTypeFacade.add(value.getMediaType(), TransactionType.MANUAL_COMMIT));
        }
    }

    public void createCreatorPersonEntities(MediaEntity entity, Media value, EntityManager session) {
        MediaCreatorPersonEntity tmpEntity;
        List<CreatorPerson> creatorPersons = value.getCreatorPersons();

        if(creatorPersons != null) {
            for(CreatorPerson item : creatorPersons) {
                if(item.getId() <= 0) {
                    CreatorPersonFacade creatorPersonFacade = new CreatorPersonFacade(session);
                    item.setId(creatorPersonFacade.add(item, TransactionType.MANUAL_COMMIT));
                }

                tmpEntity = new MediaCreatorPersonEntity();
                tmpEntity.setCreatorPersonId(item.getId());
                tmpEntity.setMediaId(entity.getId());
                session.persist(tmpEntity);
            }
        }
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);

        Query query = session.createQuery("delete MediaCreatorPersonEntity where mediaId = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        query = session.createQuery("delete MediaEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }

    protected boolean isAvailable(int id, boolean isCreate, EntityManager session) {
        Query query = session.createQuery("select count(*) from LoanEntity as l join MediaMemberEntity as m on l.mediaMemberId = m.id where m.mediaId = :id and l.closed = false");
        query.setParameter("id", id);
        query.getResultList();
        long openLoanCount = getFirstOrDefault(query);

        query = session.createQuery("select count(*) from MediaMemberEntity where mediaId = :id");
        query.setParameter("id", id);
        long maxMediaMembers = getFirstOrDefault(query);

        openLoanCount += isCreate ? 1 : -1;
        boolean isAvailable = openLoanCount < maxMediaMembers;

        return isAvailable;
    }
}