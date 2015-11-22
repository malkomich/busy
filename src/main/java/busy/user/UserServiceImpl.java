package busy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
