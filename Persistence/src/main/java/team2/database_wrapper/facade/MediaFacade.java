package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.MediaCreatorPersonEntity;
import team2.database_wrapper.entities.MediaEntity;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.CreatorPerson;
import team2.domain.entities.Media;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
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

    private MediaEntity getEntityById(int id, boolean includeInactive) {
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
        MediaEntity entity = getEntityById(id, false);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Media.class);
        }

        return null;
    }

    @Override
    public List<Media> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaEntity");
        List<MediaEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, typeList.getClass());
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

        Collection<MediaCreatorPersonEntity> mediaCreators = entity.getMediaCreatorPeopleById();
        ModelMapper mapper = MapperHelper.getMapper();

        if(mediaCreators != null) {
            for(MediaCreatorPersonEntity item : mediaCreators) {
                if(item.getId() <= 0) {
                    CreatorPersonFacade creatorPersonFacade = new CreatorPersonFacade(session);
                    entity.setMediaTypeId(creatorPersonFacade.add(mapper.map(item, CreatorPerson.class), TransactionType.MANUAL_COMMIT));
                }
            }
        }
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete MediaEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}