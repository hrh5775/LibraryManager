package team2.application.interfaces;

import java.util.List;

public abstract class BaseApplicationFacade<D> implements Session {
    /**
     * Get the entity by its ID
     * @param id
     * @return
     */
    public abstract D getByID(int id);

    /**
     * Gets a list of all entities
     * @return
     */
    public abstract List<D> getList();
}