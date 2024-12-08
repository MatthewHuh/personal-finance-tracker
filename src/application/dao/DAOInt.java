package application.dao;

/**
 * A generic Data Access Object (DAO) interface that defines basic operations
 * for creating and updating objects of type {@code Type}.
 * The actual persistence mechanism (e.g., CSV, database, etc.) is left
 * to the implementing classes.
 *
 * @param <Type> The type of the object that the DAO will manage.
 */
public interface DAOInt<Type> {
    
    /**
     * Creates a new record for the given object.
     * Implementations should handle the logic for writing
     * the object’s data to the underlying storage (e.g., CSV, database).
     *
     * @param obj The object to be persisted.
     */
    void create(Type obj);
    
    /**
     * Updates an existing record with new values.
     * Implementations should handle the logic of locating the current record,
     * replacing it with the updated values, and committing the changes to the underlying storage.
     *
     * @param curr   The current object as stored.
     * @param update The object with updated values.
     */
    void update(Type curr, Type update);
}