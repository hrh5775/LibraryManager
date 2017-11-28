package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.entities.ReservationEntity;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.entities.Reservation;
import at.team2.domain.enums.properties.ReservationProperty;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class ReservationFacade extends BaseDatabaseFacade<Reservation, ReservationProperty> {
    private static final Type type = new TypeToken<List<Reservation>>() {}.getType();

    public ReservationFacade() {
        super();
    }

    public ReservationFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Reservation getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from ReservationEntity where id = :id and closed = false");
        query.setParameter("id", id);
        ReservationEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Reservation.class);
        }

        return null;
    }

    @Override
    public List<Reservation> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from ReservationEntity where closed = false");
        List<ReservationEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(ReservationProperty property) {
        switch (property) {
            case ID:
                return "id";
            case CLOSED:
                return "closed";
            case CUSTOMER:
                return "concat(customerByCustomerId.firstName, ' ', customerByCustomerId.lastName)";
            case MEDIA:
                return "mediaByMediaId.title";
            case INFORMATION_DATE:
                return "informationDate";
            case RESERVATION_DATE:
                return "reservationDate";
            case CUSTOMER__ID:
                return "customerId";
            case MEDIA__ID:
                return "mediaId";
        }

        return null;
    }

    @Override
    public List<Reservation> filter(FilterConnector<ReservationProperty, ReservationProperty> filterConnector) {
        Query query = getByFilter("from ReservationEntity where closed = false and", getCurrentSession(), filterConnector);
        List<ReservationEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    public int add(Reservation value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        ReservationEntity entity = mapper.map(value, ReservationEntity.class);

        // do not let the user create a closed loan
        entity.setClosed(false);
        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Reservation value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        ReservationEntity entity = mapper.map(value, ReservationEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete ReservationEntity where id = :id and closed = false");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}