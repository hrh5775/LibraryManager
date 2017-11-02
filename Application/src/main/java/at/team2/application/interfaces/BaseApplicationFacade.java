package at.team2.application.interfaces;

import at.team2.common.interfaces.BaseDtoEntity;
import at.team2.domain.interfaces.BaseDomainEntity;
import at.team2.domain.interfaces.DomainEntityProperty;

import java.util.List;

public abstract class BaseApplicationFacade<D extends BaseDomainEntity, T extends BaseDtoEntity, P extends DomainEntityProperty> implements Session, Editable<T, P> {
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