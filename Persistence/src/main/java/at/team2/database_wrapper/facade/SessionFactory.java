package at.team2.database_wrapper.facade;

import org.hibernate.HibernateException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SessionFactory {
    private static EntityManagerFactory _entityManagerFactory;

    private SessionFactory() {
    }

    public static EntityManager createSession() throws HibernateException {
        if (_entityManagerFactory == null) {
            try {
                _entityManagerFactory = Persistence.createEntityManagerFactory("library");
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }

        _entityManagerFactory.getCache().evictAll();
        EntityManager session = _entityManagerFactory.createEntityManager();
        //manager.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        session.clear();

        return session;
    }

    public static void close() {
        if (_entityManagerFactory != null) {
            _entityManagerFactory.close();
        }
    }
}