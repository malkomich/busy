package busy.location;

import java.util.List;

public interface LocationService {

	/**
	 * Retrieves all countries available.
	 * 
	 * @return
	 */
	List<Country> findCountries();

	/**
	 * Find all cities which belong to the given country.
	 * 
	 * @param country
	 * @return
	 */
	List<City> findCitiesByCountryCode(String countryCode);

	/**
	 * Retrieves the City with the given ID, or null if not exists in database.
	 * 
	 * @param parseInt
	 * @return
	 */
	City findCityById(int parseInt);

	/**
	 * Saves the address into the database.
	 * 
	 * @param address
	 */
	void saveAddress(Address address);

}
