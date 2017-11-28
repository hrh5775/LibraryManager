package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.entities.MediaEntity;
import at.team2.database_wrapper.entities.MediaMemberEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.LoanProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.LoanEntity;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Loan;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class LoanFacade extends BaseDatabaseFacade<Loan, LoanProperty> {
    private static final Type type = new TypeToken<List<Loan>>() {}.getType();

    public LoanFacade() {
    }

    public LoanFacade(EntityManager session) {
        super(session);
    }

    private LoanEntity getEntityByIdWithClosed(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from LoanEntity where id = :id");
        query.setParameter("id", id);

        return getFirstOrDefault(query);
    }

    protected LoanEntity getEntityById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from LoanEntity where id = :id and closed = false");
        query.setParameter("id", id);

        return getFirstOrDefault(query);
    }

    @Override
    public Loan getById(int id) {
        LoanEntity entity = getEntityById(id);

        if (entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Loan.class);
        }

        return null;
    }

    @Override
    public List<Loan> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from LoanEntity where closed = false");
        List<LoanEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(LoanProperty property) {
        switch (property) {
            case ID:
                return "id";
            case CLOSED:
                return "closed";
            case CUSTOMER:
                return "concat(customerByCustomerId.firstName, ' ', customerByCustomerId.lastName)";
            case END:
                return "end";
            case LAST_RENEWAL_START:
                return "lastRenewalStart";
            case MEDIA_MEMBER:
                return "mediaMemberByMediaMemberId.mediaByMediaId.title";
            case START:
                return "start";
            case REMINDER:
                return "reminderByReminderId.reminderDate";
            case CUSTOMER__ID:
                return "customerId";
            case MEDIA__ID:
                return "mediaMemberByMediaMemberId.mediaId";
        }

        return null;
    }

    @Override
    public List<Loan> filter(FilterConnector<LoanProperty, LoanProperty> filterConnector) {
        Query query = getByFilter("from LoanEntity where closed = false and", getCurrentSession(), filterConnector);
        List<LoanEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(Loan value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        LoanEntity entity = mapper.map(value, LoanEntity.class);

        createReminder(entity, value, session);
        // do not let the user create a closed loan
        entity.setClosed(false);
        session.persist(entity);

        // @todo: perhaps in a other method
        MediaMemberFacade mediaMemberFacade = new MediaMemberFacade(getCurrentSession());
        MediaMemberEntity mediaMemberEntity =  mediaMemberFacade.getEntityById(entity.getMediaMemberId());

        MediaFacade mediaFacade = new MediaFacade(session);
        MediaEntity mediaEntity = mediaFacade.getEntityById(mediaMemberEntity.getMediaId());
        mediaEntity.setAvailable(mediaFacade.isAvailable(mediaEntity.getId(), true, session));
        session.merge(mediaEntity);

        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Loan value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        LoanEntity entity = mapper.map(value, LoanEntity.class);

        // the specific media and customer can't be changed in this method
        createReminder(entity, value, session);
        session.merge(entity);

        // @todo: perhaps in a other method
        MediaMemberFacade mediaMemberFacade = new MediaMemberFacade(session);
        MediaMemberEntity mediaMemberEntity =  mediaMemberFacade.getEntityById(entity.getMediaMemberId());

        MediaFacade mediaFacade = new MediaFacade(session);
        MediaEntity mediaEntity = mediaFacade.getEntityById(mediaMemberEntity.getMediaId());
        mediaEntity.setAvailable(mediaFacade.isAvailable(mediaEntity.getId(), false, session));
        session.merge(mediaEntity);

        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    private void createReminder(LoanEntity entity, Loan value, EntityManager session) {
        if(entity.getReminderId() != null && value.getReminder() != null && entity.getReminderId() <= 0) {
            ReminderFacade reminderFacade = new ReminderFacade(session);
            entity.setReminderId(reminderFacade.add(value.getReminder(), TransactionType.MANUAL_COMMIT));
        }
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        LoanEntity entity = getEntityById(id);

        if(entity != null) {
            MediaEntity mediaEntity = entity.getMediaMemberByMediaMemberId().getMediaByMediaId();

            Query query = session.createQuery("delete LoanEntity where id = :id and closed = false");
            query.setParameter("id", id);
            query.executeUpdate();

            if(mediaEntity != null) {
                MediaFacade mediaFacade = new MediaFacade(session);
                mediaEntity.setAvailable(mediaFacade.isAvailable(mediaEntity.getId(), false, session));
                session.merge(mediaEntity);

                return StoreHelper.storeEntities(session, transactionType);
            }
        }

        return false;
    }
}