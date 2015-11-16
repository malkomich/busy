package busy.location;

import java.util.List;

interface CountryDao {

	/**
	 * Finds the collection of all registries of Country in the database. It
	 * will return an empty List when no Users exist.
	 * 
	 * @return List of existing Countrys
	 */
	List<Country> findAll();
	
	/**
	 * Create or update a Country registry in the database.
	 * 
	 * @param country
	 */
	void save(Country country);

}
