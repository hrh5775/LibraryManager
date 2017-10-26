package team2.database_wrapper.facade;

import org.modelmapper.ModelMapper;
import team2.database_wrapper.entities.LoanEntity;
import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.helper.MapperHelper;
import team2.database_wrapper.helper.StoreHelper;
import team2.domain.entities.Loan;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class LoanFacade extends BaseDatabaseFacade<Loan> {
    private List<Loan> listType = new LinkedList<>();

    public LoanFacade() {
        super();
    }

    public LoanFacade(EntityManager session) {
        super(session);
    }

    @Override
    public Loan getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from LoanEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<LoanEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            LoanEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, Loan.class);
        }

        return null;
    }

    @Override
    public List<Loan> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from LoanEntity");
        List<LoanEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, listType.getClass());
    }

    @Override
    public int add(Loan value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        LoanEntity entity = mapper.map(value, LoanEntity.class);

        createReminder(entity, value, session);
        // do not let the user create a closed loan
        entity.setClosed(true);
        session.persist(entity);
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
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    public void createReminder(LoanEntity entity, Loan value, EntityManager session) {
        if(entity.getReminderId() <= 0) {
            ReminderFacade reminderFacade = new ReminderFacade(session);
            entity.setReminderId(reminderFacade.add(value.getReminder(), TransactionType.MANUAL_COMMIT));
        }
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete LoanEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}