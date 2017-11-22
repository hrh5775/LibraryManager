package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.entities.LoanEntity;
import at.team2.database_wrapper.entities.MediaEntity;
import at.team2.database_wrapper.entities.MediaMemberEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.entities.Loan;
import at.team2.domain.entities.MediaMember;
import at.team2.domain.enums.properties.MediaMemberProperty;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class MediaMemberFacade extends BaseDatabaseFacade<MediaMember, MediaMemberProperty> {
    private static final Type type = new TypeToken<List<MediaMember>>() {}.getType();

    public MediaMemberFacade() {
        super();
    }

    public MediaMemberFacade(EntityManager session) {
        super(session);
    }

    protected MediaMemberEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaMemberEntity where id = :id");
        query.setParameter("id", id);

        return getFirstOrDefault(query);
    }

    @Override
    public MediaMember getById(int id) {
        MediaMemberEntity entity = getEntityById(id);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, MediaMember.class);
        }

        return null;
    }

    @Override
    public List<MediaMember> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaMemberEntity");
        List<MediaMemberEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(MediaMemberProperty property) {
        switch (property) {
            case ID:
                return "id";
            case EXTENDED_INDEX:
                return "extendedIndex";
            case MEDIA:
                return "mediaByMediaId.title";
            case FULL_INDEX:
                return "concat(mediaByMediaId.baseIndex, ' ', extendedIndex)";
            case MEDIA__ID:
                return "mediaId";
        }

        return null;
    }

    @Override
    public List<MediaMember> filter(FilterConnector<MediaMemberProperty, MediaMemberProperty> filterConnector) {
        Query query = getByFilter("from MediaMemberEntity where", getCurrentSession(), filterConnector);
        List<MediaMemberEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(MediaMember value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        MediaMemberEntity entity = mapper.map(value, MediaMemberEntity.class);

        // An existing media has to be available, which can be created with the other facades (book, dvd, e.g.) in
        // MANUAL_COMMIT mode, when necessary.
        // loans can't be created in this method
        createLoans(entity, session);
        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        MediaFacade mediaFacade = new MediaFacade(getCurrentSession());
        MediaEntity mediaEntity =  mediaFacade.getEntityById(entity.getMediaId());
        session.refresh(mediaEntity); // @todo: perhaps use another solution

        return entity.getId();
    }

    @Override
    public int update(MediaMember value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        MediaMemberEntity entity = mapper.map(value, MediaMemberEntity.class);

        createLoans(entity, session);
        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        MediaFacade mediaFacade = new MediaFacade(getCurrentSession());
        MediaEntity mediaEntity =  mediaFacade.getEntityById(entity.getMediaId());
        session.refresh(mediaEntity); // @todo: perhaps use another solution

        return entity.getId();
    }

    private void createLoans(MediaMemberEntity entity, EntityManager session) {
        Collection<LoanEntity> loans = entity.getLoansById();
        ModelMapper mapper = MapperHelper.getMapper();

        if(loans != null) {
            for(LoanEntity item : loans) {
                if(item.getId() <= 0) {
                    LoanFacade loanFacade = new LoanFacade(session);
                    item.setId(loanFacade.add(mapper.map(item, Loan.class), TransactionType.MANUAL_COMMIT));
                }
            }
        }
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        MediaMemberEntity entity = getEntityById(id);

        if(entity != null) {
            MediaEntity mediaEntity = entity.getMediaByMediaId();

            Query query = session.createQuery("delete MediaMemberEntity where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();

            if(mediaEntity != null) {
                session.refresh(mediaEntity); // @todo: perhaps use another solution
                return StoreHelper.storeEntities(session, transactionType);
            }
        }

        return false;
    }

    public MediaMember getNotLoanedMediaMember(int mediaMemberId) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from MediaMemberEntity where id = :mediaMemberId and (select count(*) from LoanEntity where mediaMemberId = :mediaMemberId and closed = false) = 0");
        query.setParameter("mediaMemberId", mediaMemberId);
        MediaMemberEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, MediaMember.class);
        }

        return null;
    }
}