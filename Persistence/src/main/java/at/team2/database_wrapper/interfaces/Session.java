package at.team2.database_wrapper.interfaces;

import at.team2.database_wrapper.enums.TransactionType;
import javax.persistence.EntityManager;

public interface Session {
    public void setSession(EntityManager session);

    /**
     * Closes the session
     */
    public void closeSession();

    /**
     * Gets the current database session
     * @return database session
     */
    public EntityManager getCurrentSession();

    /**
     * Gets the current database session
     * @param transactionType   default value: AUTO_COMMIT
     * @return database session
     */
    public EntityManager getCurrentSession(TransactionType transactionType);
}