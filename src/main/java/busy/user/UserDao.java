package busy.user;

import java.util.List;

/**
 * User persistence interface.
 * 
 * @author malkomich
 */
interface UserDao {

    /**
     * Gets the collection of all current user objects.
     * 
     * @return The list of current users
     */
    List<User> findAll();

    /**
     * Gets an existing user given his email address.
     * 
     * @param email
     *            email address of the user
     * @return Unique user corresponding to this email
     */
    User findByEmail(String email);

    /**
     * Persists a new user or update an existing one.
     * 
     * @param user
     *            the user object to be persisted
     */
    void save(User user);

}
