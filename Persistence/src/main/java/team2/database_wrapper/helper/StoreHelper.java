package team2.database_wrapper.helper;

import team2.database_wrapper.enums.TransactionType;

import javax.persistence.EntityManager;

public class StoreHelper {
    public static boolean storeEntities(EntityManager session, TransactionType transactionType) {
        if(transactionType == TransactionType.AUTO_COMMIT) {
            try {
                session.flush();
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
            }

            return false;
        }

        return true;
    }
}
