package busy.location;

import java.util.List;

/**
 * Location service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface LocationService {

    /**
     * Gets all the countries currently available.
     * 
     * @return The list of current countries
     */
    List<Country> findCountries();

    /**
     * Gets all the cities which belong to the given country.
     * 
     * @param countryCode
     *            the country which resultant city belong to
     * @return The resultant city
     */
    List<City> findCitiesByCountryCode(String countryCode);

    /**
     * Gets the city with the given ID.
     * 
     * @param cityId
     *            unique ID of the City
     * @return The resultant city
     */
    City findCityById(int cityId);

    /**
     * Saves or updates an address.
     * 
     * @param address
     *            the address to be saved
     */
    void saveAddress(Address address);

}
