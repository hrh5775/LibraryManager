package team2.database_wrapper.interfaces;

import javax.persistence.EntityManager;

public interface Session {
    /**
     * Closes the session
     */
    public void closeSession();

    /**
     * Gets the current database session
     * @return database session
     */
    public EntityManager getCurrentSession();
}