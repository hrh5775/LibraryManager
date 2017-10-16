package team2.database_wrapper.interfaces;

public interface Editeable<V> {
    /**
     * Adds the new entity
     * @param value entity
     * @return  ID of the new entity
     *          <= 0 for an error
     */
    public int add(V value);

    /**
     * Updates an existing entity
     * @param value entity
     * @return  ID of the entity
     *          <= 0 for an error
     */
    public int update(V value);

    /**
     * Deletes an existing entity
     * @param id    ID of the entity
     * @return  false when something went wrong
     */
    public boolean delete(int id);
}
