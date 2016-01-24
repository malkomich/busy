package busy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RegistryDao registryDao;

	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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

		saveUser(user);

		registryDao.delete(user.getId());
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busy.user.UserService#createVerificationToken(busy.user.User,
	 * java.lang.String)
	 */
	@Override
	public void createVerificationToken(User user, String token) {

		registryDao.save(user.getId(), token);
		
	}

	/* (non-Javadoc)
	 * @see busy.user.UserService#getVerification(java.lang.String)
	 */
	@Override
	public Verification getVerification(String token) {
		
		return registryDao.findByToken(token);
	}

}
