package busy.user;

import java.util.List;

interface UserDao {

	/**
	 * Finds the collection of all registries of User in the database. It will
	 * return an empty List when no Users exist.
	 * 
	 * @return List of existing Users
	 */
	List<User> findAll();

	/**
	 * Finds an existing User from the database. It will return null when no
	 * existing User have this value of email.
	 * 
	 * @param email
	 *            User's email.
	 * @return Unique User corresponding to this email.
	 */
	User findByEmail(String email);

	/**
	 * Create or update a User registry in the database.
	 * 
	 * @param user
	 */
	void save(User user);

}
