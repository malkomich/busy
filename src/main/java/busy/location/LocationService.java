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

}
