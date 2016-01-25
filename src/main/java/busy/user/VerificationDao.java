package busy.user;

public interface VerificationDao {

	/**
	 * Save a new validation token for the user account to be confirmed by
	 * email.
	 * 
	 * @param userId
	 * @param token
	 */
	void save(int userId, String token);

	/**
	 * Delete the validation from the database when user has confirmed his email
	 * address.
	 * 
	 * @param userId
	 */
	void deleteByUserId(int userId);

	/**
	 * Gets the token instance to confirm that it exists to validate the
	 * account.
	 * 
	 * @param token
	 * @return
	 */
	Verification findByToken(String token);

}
