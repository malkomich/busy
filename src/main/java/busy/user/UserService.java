package busy.user;

public interface UserService {

	/**
	 * Obtains a User from his email.
	 * 
	 * @param email
	 * @return
	 */
	User findUserByEmail(String email);

	/**
	 * Saves a new User to the database with the fields from the given
	 * SignupForm.
	 * 
	 * @param form
	 *            Set of fields of User data
	 */
	void saveUser(User user);

	/**
	 * Set active attribute to true for the given User, and delete the Registry
	 * asociated to this User from the database.
	 * 
	 * @param user
	 */
	void confirmUser(User user);

	/**
	 * Create Registry verification data.
	 * 
	 * @param user
	 * @param token
	 */
	void createVerificationToken(User user, String token);

	/**
	 * Get the verification instance with the related User.
	 * 
	 * @param token
	 * @return
	 */
	Verification getVerification(String token);

}
