package busy.user;

public interface UserService {

	/**
	 * Obtains a User from his email.
	 * 
	 * @param email
	 * @return
	 */
	User findUserByEmail(String email);
}
