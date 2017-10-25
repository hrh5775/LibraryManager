package team2.database_wrapper.facade;

import team2.database_wrapper.enums.TransactionType;
import team2.database_wrapper.interfaces.Editeable;
import team2.database_wrapper.interfaces.Session;
import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseDatabaseFacade<V> implements Session, Editeable<V> {
    private EntityManager _session;

    public BaseDatabaseFacade() {
        this(SessionFactory.getSession());
    }

    public BaseDatabaseFacade(EntityManager session) {
        _session = session;
    }

    public abstract V getById(int id);
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