package at.team2.domain.entities;

import at.team2.domain.interfaces.DomainEntity;
import at.team2.domain.interfaces.DomainEntityProperty;
import at.team2.domain.interfaces.Validate;

public abstract class BaseDomainEntity<P extends DomainEntityProperty> implements DomainEntity, Validate<P> {
    /*private EntityLogic _logic;

    public BaseDomainEntity(EntityType entityType) {
        _logic = LogicFactory.getLogic(entityType);
    }

    @Override
    public List<Pair<String, String>> validate() {
        return _logic.validate(this);
    }*/
}
