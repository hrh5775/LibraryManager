package at.team2.database_wrapper.facade;

import at.team2.database_wrapper.enums.TransactionType;
import org.modelmapper.ModelMapper;
import at.team2.database_wrapper.entities.LoanConditionEntity;
import at.team2.database_wrapper.helper.MapperHelper;
import at.team2.database_wrapper.helper.StoreHelper;
import at.team2.domain.entities.LoanCondition;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public class LoanConditionFacade extends BaseDatabaseFacade<LoanCondition> {
    private static List<LoanCondition> listType = new LinkedList<>();

    public LoanConditionFacade() {
        super();
    }

    public LoanConditionFacade(EntityManager session) {
        super(session);
    }

    @Override
    public LoanCondition getById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from LoanConditionEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<LoanConditionEntity> entities = query.getResultList();

        if (entities.size() > 0) {
            LoanConditionEntity entity = entities.get(0);

            ModelMapper mapper = MapperHelper.getMapper();
            return mapper.map(entity, LoanCondition.class);
        }

        return null;
    }

    @Override
    public List<LoanCondition> getList() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from LoanConditionEntity ");
        List<LoanConditionEntity> entities = query.getResultList();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(entities, listType.getClass());
    }

    @Override
    public int add(LoanCondition value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        LoanConditionEntity entity = mapper.map(value, LoanConditionEntity.class);

        session.persist(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public int update(LoanCondition value, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        ModelMapper mapper = MapperHelper.getMapper();
        LoanConditionEntity entity = mapper.map(value, LoanConditionEntity.class);

        session.merge(entity);
        StoreHelper.storeEntities(session, transactionType);

        return entity.getId();
    }

    @Override
    public boolean delete(int id, TransactionType transactionType) {
        EntityManager session = getCurrentSession(transactionType);
        Query query = session.createQuery("delete LoanConditionEntity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return StoreHelper.storeEntities(session, transactionType);
    }
}