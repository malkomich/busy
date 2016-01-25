package busy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private VerificationDao verificationDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setValidationDao(VerificationDao validationDao) {
		this.verificationDao = validationDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busy.user.UserService#findUserByEmail(java.lang.String)
	 */
	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busy.user.UserService#saveUser(busy.user.User)
	 */
	@Override
	public void saveUser(User user) {

		userDao.save(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busy.user.UserService#confirmUser(busy.user.User)
	 */
	@Override
	public void confirmUser(User user) {

		if (userDao.findByEmail(user.getEmail()) == null)
			throw new IllegalArgumentException("The user to confirm does not exist.");
		
		user.setActive(true);
		saveUser(user);

		verificationDao.deleteByUserId(user.getId());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busy.user.UserService#createVerificationToken(busy.user.User,
	 * java.lang.String)
	 */
	@Override
	public void createVerificationToken(User user, String token) {

		verificationDao.save(user.getId(), token);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busy.user.UserService#getVerification(java.lang.String)
	 */
	@Override
	public Verification getVerification(String token) {

		return verificationDao.findByToken(token);
	}

}
