package busy.user;

/**
 * Verification persistence interface.
 * 
 * @author malkomich
 */
public interface VerificationDao {

    /**
     * Persists a new verification or updates a new one. The verification instance will be attached
     * to a user with the given userId, and may be referenced by the given String token.
     * 
     * @param userId
     *            unique ID of the user
     * @param token
     *            unique String token
     */
    void save(int userId, String token);

    /**
     * Deletes the verification object given the id of the user attached to this validation.
     * 
     * @param userId
     *            unique ID of the user
     */
    void deleteByUserId(int userId);

    /**
     * Gets the verification instance attached to the given token.
     * 
     * @param token
     *            unique String token
     * @return the resultant verification object
     */
    Verification findByToken(String token);

}
