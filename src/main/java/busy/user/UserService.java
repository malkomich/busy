package busy.user;

import java.util.List;

/**
 * User service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface UserService {

    /**
     * Gets a user given his email address.
     * 
     * @param email
     *            email address of the user
     * @return The resultant user
     */
    User findUserByEmail(String email);

    /**
     * Saves or updates a user.
     * 
     * @param user
     *            the user to be saved
     */
    void saveUser(User user);

    /**
     * Activates the given user and deletes the verification associated to this user.
     * 
     * @param user
     *            the user to be confirmed
     */
    void confirmUser(User user);

    /**
     * Creates a new verification for the given user.
     * 
     * @param user
     *            the user to attach the verification
     * @param token
     *            unique String token
     */
    void createVerificationToken(User user, String token);

    /**
     * Gets the verification given the token which was used to create it.
     * 
     * @param token
     *            unique String token
     * @return The resultant verification
     */
    Verification getVerification(String token);

    /**
     * Change the active status of an user with the given ID.
     * 
     * @param userId
     *            unique ID of the user
     * @param active
     *            active status
     */
    void toogleUserActiveStatus(int userId, boolean active);

    /**
     * Retrieves all the registered users.
     * 
     * @return The list of resultant users.
     */
    List<User> findAll();

}
