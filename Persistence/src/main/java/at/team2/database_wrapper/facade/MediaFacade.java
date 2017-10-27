package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.entities.MediaCreatorPersonEntity;
import at.team2.database_wrapper.entities.MediaEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import org.modelmapper.ModelMapper;
import at.team2.domain.entities.CreatorPerson;
import at.team2.domain.entities.Media;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class MediaFacade extends BaseDatabaseFacade<Media> {
    private List<Media> typeList = new LinkedList<>();

    protected MediaFacade() {
        super();
    }

    protected MediaFacade(EntityManager session) {
        super(session);
    }

    private MediaEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<MediaEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
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
        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        createCreatorPersonEntities(entity, value, session);

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
                if(item.getID() <= 0) {
                    CreatorPersonFacade creatorPersonFacade = new CreatorPersonFacade(session);
                    item.setId(creatorPersonFacade.add(item, TransactionType.MANUAL_COMMIT));
                }

                tmpEntity = new MediaCreatorPersonEntity();
                tmpEntity.setCreatorPersonId(item.getID());
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
}