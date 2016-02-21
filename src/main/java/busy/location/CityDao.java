package busy.location;

import java.util.List;

interface CityDao {

	/**
	 * Finds the collection of all registries of City in the database. It will
	 * return an empty List when no Cities exist.
	 * 
	 * @return List of existing Cities
	 */
	List<City> findAll();

	/**
	 * Finds the collection of all registries of City in the database which
	 * belong to the country given. It will return an empty List when no Cities
	 * fulfill the restriction.
	 * 
	 * @param country
	 * @return
	 */
	List<City> findByCountryCode(String countryCode);

	/**
	 * Create or update a City registry in the database.
	 * 
	 * @param city
	 */
	void save(City city);

	/**
	 * Retrieves the City with the given ID
	 * 
	 * @param parseInt
	 * @return
	 */
	City findById(int parseInt);

}
