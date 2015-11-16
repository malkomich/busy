package busy.location;

import java.util.List;

interface CityDao {

	/**
	 * Finds the collection of all registries of City in the database. It will
	 * return an empty List when no Users exist.
	 * 
	 * @return List of existing Citys
	 */
	List<City> findAll();

	/**
	 * Create or update a City registry in the database.
	 * 
	 * @param city
	 */
	void save(City city);

}
