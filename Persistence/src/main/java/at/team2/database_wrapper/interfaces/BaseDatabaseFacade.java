package at.team2.database_wrapper.interfaces;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.SessionFactory;
import at.team2.domain.interfaces.BaseDomainEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public abstract class BaseDatabaseFacade<V extends BaseDomainEntity> implements Session, Editable<V> {
    private EntityManager _session;

    public BaseDatabaseFacade() {
        this(SessionFactory.getSession());
    }

    public BaseDatabaseFacade(EntityManager session) {
        _session = session;
    }

    public abstract V getById(int id);

    protected <T> T getFirstOrDefault(Query query) {
        query.setMaxResults(1);
        List<T> entities = query.getResultList();

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
    }

    public abstract List<V> getList();

    @Override
    public void closeSession() {
        if(_session != null) {
            _session.close();
            _session = null;
        }
    }

    @Override
    public EntityManager getCurrentSession() {
        return _session;
    }

    @Override
    public EntityManager getCurrentSession(TransactionType transactionType) {
        if(transactionType == TransactionType.AUTO_COMMIT) {
            _session.getTransaction().begin();
        }

        return _session;
    }
}