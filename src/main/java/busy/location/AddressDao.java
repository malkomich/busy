package busy.location;

import java.util.List;

interface AddressDao {

	/**
	 * Finds the collection of all registries of Address in the database. It
	 * will return an empty List when no Address exist.
	 * 
	 * @return List of existing Addresss
	 */
	List<Address> findAll();

	/**
	 * Create or update a Address registry in the database.
	 * 
	 * @param address
	 */
	void save(Address address);

}
