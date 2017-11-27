package at.team2.application.interfaces;

import at.team2.common.interfaces.BaseDtoEntity;
import at.team2.database_wrapper.facade.SessionFactory;
import at.team2.domain.interfaces.BaseDomainEntity;
import at.team2.domain.interfaces.DomainEntityProperty;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseApplicationFacade<D extends BaseDomainEntity, T extends BaseDtoEntity, U extends BaseDtoEntity, P extends DomainEntityProperty> implements Session, Editable<T, U, P> {
    private EntityManager _session;

    public BaseApplicationFacade() {
        _session = SessionFactory.createSession();
    }

    /**
     * Get the entity by its Id
     * @param id
     * @return
     */
    public abstract D getById(int id);

    /**
     * Gets a list of all entities
     * @return
     */
    public abstract List<D> getList();

    @Override
    public void closeSession() {
        if(_session != null && _session.isOpen()) {
            _session.close();
        }

        _session = null;
    }

    protected EntityManager getSession() {
        return _session;
    }
}