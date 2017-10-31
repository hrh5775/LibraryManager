package at.team2.application.interfaces;

import at.team2.connector.interfaces.BaseDtoEntity;
import at.team2.domain.interfaces.DomainEntityProperty;
import javafx.util.Pair;

import java.util.List;

public interface Editable<V extends BaseDtoEntity, T extends DomainEntityProperty> {
    /**
     * Adds the new entity
     * @param value entity
     * @return  a list of errors which is always set and an integer for the ID of the new entityID
     *          <= 0 for an error
     */
    public Pair<Integer, List<Pair<T, String>>> add(V value);

    /**
     * Updates an existing entity
     * @param value entity
     * @return  a list of errors which is always set and an integer for the ID of the entity
     *          <= 0 for an error
     */
    public Pair<Integer, List<Pair<T, String>>> update(V value);

    /**
     * Deletes an existing entity
     * @param id  ID of the entity
     * @return  a list of errors which is always set and a boolean value to indicate the status
     */
    public Pair<Boolean, List<Pair<T, String>>> delete(int id);
}