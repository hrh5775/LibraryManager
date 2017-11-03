package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.common.FilterItem;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.interfaces.BaseDatabaseFacade;
import at.team2.domain.enums.properties.ReminderProperty;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.ReminderEntity;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.Reminder;
import org.modelmapper.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Type;
import java.util.List;

public class ReminderFacade extends BaseDatabaseFacade<Reminder, ReminderProperty> {
    private static final Type type = new TypeToken<List<Reminder>>() {}.getType();

    public ReminderFacade() {
        super();
    }

    public ReminderFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Reminder getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from ReminderEntity where id = :id");
        query.setParameter("id", id);
        ReminderEntity entity = getFirstOrDefault(query);

        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Reminder.class);
        }

        return null;
    }

    @Override
    public List<Reminder> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from ReminderEntity");
        List<ReminderEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, type);
    }

    @Override
    protected String getColumnNameForProperty(ReminderProperty property) {
        switch (property) {
            case ID:
                return "id";
            case REMINDER_COUNT:
                return "reminderCount";
            case REMINDER_DATE:
                return "reminderDate";
        }

        return null;
    }

    @Override
    public List<Reminder> filter(List<FilterItem<ReminderProperty>> filterItems) {
        // @todo: implement
        return null;
    }

    @Override
    public int add(Reminder value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        ReminderEntity entity = mapper.map(value, ReminderEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(Reminder value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        ReminderEntity entity = mapper.map(value, ReminderEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete ReminderEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}