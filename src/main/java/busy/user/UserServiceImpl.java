package busy.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service logic implementation.
 * 
 * @author malkomich
 *
 */
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
     * @see busy.user.UserService#findUserByEmail(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {

        return userDao.findByEmail(email);
    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserService#saveUser(busy.user.User)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveUser(User user) {

        userDao.save(user);

    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserService#confirmUser(busy.user.User)
     */
    @Override
    public void confirmUser(User user) {

        if (findUserByEmail(user.getEmail()) == null)
            throw new IllegalArgumentException("The user to confirm does not exist.");

        user.setActive(true);
        saveUser(user);

    }

    /**
     * Deletes the verification item associate to the given user.
     * 
     * @param user
     *            the user attached to the verification
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    private void deleteVerificationByUser(User user) {

        verificationDao.deleteByUserId(user.getId());
    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserService#createVerificationToken(busy.user.User, java.lang.String)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void createVerificationToken(User user, String token) {

        verificationDao.save(user.getId(), token);

    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserService#getVerification(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Verification getVerification(String token) {

        return verificationDao.findByToken(token);
    }

    @Override
    public void toogleUserActiveStatus(int userId, boolean active) {

        userDao.changeActiveStatus(userId, active);
    }

    @Override
    public List<User> findAll() {

        return userDao.findAll();
    }

}
