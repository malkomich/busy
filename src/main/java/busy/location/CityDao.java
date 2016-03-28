package busy.location;

import java.util.List;

/**
 * City persistence interface.
 * 
 * @author malkomich
 */
interface CityDao {

    /**
     * Gets the list of all current city objects.
     * 
     * @return The list of existing cities
     */
    List<City> findAll();

    /**
     * Gets the list of city objects which belong to the country with the given code.
     * 
     * @param countryCode
     *            code attached to the country
     * @return The list of resultant cities
     */
    List<City> findByCountryCode(String countryCode);

    /**
     * Persists a new city or updated an existing one.
     * 
     * @param city
     *            The city object to be persisted
     */
    void save(City city);

    /**
     * Gets the city object with the given ID
     * 
     * @param cityId
     *            unique ID of the city
     * @return The resultant city object
     */
    City findById(int cityId);

}
