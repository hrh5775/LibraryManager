package at.team2.database_wrapper.interfaces;

import at.team2.database_wrapper.enums.TransactionType;
import at.team2.domain.interfaces.BaseDomainEntity;

public interface Editable<V extends BaseDomainEntity> {
    /**
     * Adds the new entity
     * @param value entity
     * @param transactionType transactionType   default value: AUTO_COMMIT
     * @return  ID of the new entity
     *          <= 0 for an error
     */
    public int add(V value, TransactionType transactionType);

    /**
     * Updates an existing entity
     * @param value entity
     * @param transactionType transactionType   default value: AUTO_COMMIT
     * @return  ID of the entity
     *          <= 0 for an error
     */
    public int update(V value, TransactionType transactionType);

    /**
     * Deletes an existing entity
     * @param id    ID of the entity
     * @param transactionType transactionType   default value: AUTO_COMMIT
     * @return  false when something went wrong
     */
    public boolean delete(int id, TransactionType transactionType);
}