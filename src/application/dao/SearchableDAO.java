package application.dao;

import java.util.List;

/**
 * A generic interface that defines the ability to search for records of a given type.
 * Implementing classes should provide a way to search through their data storage
 * for entries that contain a specified substring.
 *
 * @param <Type> The type of object that the DAO manages and can search through.
 */
public interface SearchableDAO<Type> {
    
    /**
     * Searches through all stored entries and returns those whose data contains the specified substring.
     *
     * @param subStr The substring to search for within the stored objects.
     * @return A list of objects that contain the given substring in their identifying field(s).
     */
    List<Type> search(String subStr);
}