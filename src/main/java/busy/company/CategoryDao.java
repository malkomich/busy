package busy.company;

import java.util.List;

/**
 * Category persistence interface.
 * 
 * @author malkomich
 */
public interface CategoryDao {

    /**
     * Persists a new category object or updates an existing one.
     * 
     * @param category
     *            The category object to be persisted
     */
    void save(Category category);

    /**
     * Gets the category object by his ID.
     * 
     * @param categoryId
     *            unique ID of the category
     * @return The resultant category object
     */
    Category findById(int categoryId);

    /**
     * Gets the list of all current category objects.
     * 
     * @return The list of current categories
     */
    List<Category> findAll();

}
