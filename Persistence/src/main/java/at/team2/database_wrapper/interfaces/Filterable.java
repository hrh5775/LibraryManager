package at.team2.database_wrapper.interfaces;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.domain.interfaces.BaseDomainEntity;
import at.team2.domain.interfaces.DomainEntityProperty;

import java.util.List;

public interface Filterable<V extends BaseDomainEntity, L extends DomainEntityProperty, R extends DomainEntityProperty> {
    /**
     * Filters the list on the DB instead of the local host
     * @param filterConnector   a filter connector for the search
     * @return  a filtered list
     */
    public List<V> filter(FilterConnector<L, R> filterConnector);
}