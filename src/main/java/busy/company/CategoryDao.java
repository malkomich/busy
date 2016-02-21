package busy.company;

import java.util.List;

public interface CategoryDao {

	/**
	 * Create or update a Category registry in the database.
	 * 
	 * @param category
	 */
	void save(Category category);

	/**
	 * Finds the Category in the database by his primary key. It will return
	 * null when the Category is not found.
	 * 
	 * @param categoryId
	 * @return
	 */
	Category findById(int categoryId);

	/**
	 * Finds all the Categories from the database.
	 * 
	 * @return
	 */
	List<Category> findAll();

}
