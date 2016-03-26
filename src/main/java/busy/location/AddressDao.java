package busy.location;

import java.util.List;

/**
 * Address persistence interface.
 * 
 * @author malkomich
 */
interface AddressDao {

    /**
     * Gets the list of all current address objects.
     * 
     * @return The list of current addresses
     */
    List<Address> findAll();

    /**
     * Persists a new address object or updates an existing one.
     * 
     * @param address
     *            The address object to be persisted
     */
    void save(Address address);

}
