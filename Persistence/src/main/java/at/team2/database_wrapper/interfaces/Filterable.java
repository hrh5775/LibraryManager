package at.team2.database_wrapper.interfaces;

import at.team2.database_wrapper.common.FilterItem;
import at.team2.domain.interfaces.BaseDomainEntity;
import at.team2.domain.interfaces.DomainEntityProperty;

import java.util.List;

public interface Filterable<V extends BaseDomainEntity, P extends DomainEntityProperty> {
    /**
     * Filters the list on the DB instead of the local host
     * @param filterItems   a list of settings for the search
     * @return  a filtered list
     */
    public List<V> filter(List<FilterItem<P>> filterItems);
}