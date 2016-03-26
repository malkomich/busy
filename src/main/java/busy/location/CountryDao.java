package busy.location;

import java.util.List;

/**
 * Country persistence interface.
 * 
 * @author malkomich
 */
interface CountryDao {

    /**
     * Gets the list of all current country objects.
     * 
     * @return The list of current countries
     */
    List<Country> findAll();

    /**
     * Persists a new country or update an existing one.
     * 
     * @param country
     *            the country object to be persisted
     */
    void save(Country country);

}
