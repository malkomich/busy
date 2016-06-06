package busy.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import busy.Application;

/**
 * Test Case for the UserService implementation class.
 * 
 * @author malkomich
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceTest {

    private UserServiceImpl service;

    private UserDao userDao;
    private VerificationDao validationDao;

    @Before
    public void setUp() {
        
        service = new UserServiceImpl();
        
        userDao = mock(UserDao.class);
        service.setUserDao(userDao);

        validationDao = mock(VerificationDao.class);
        service.setValidationDao(validationDao);
    }

    /**
     * Try to find a user whose email does not exists in the database.
     */
    @Test
    public void findUserWrong() {
        String wrongEmail = "asdf@busy.com";
        when(userDao.findByEmail(wrongEmail)).thenReturn(null);

        User user = service.findUserByEmail(wrongEmail);
        assertNull(user);
    }

    /**
     * Find a user whose email exists in the database.
     */
    @Test
    public void findUserSuccess() {
        String email = "user@domain.com";
        when(userDao.findByEmail(email))
                .thenReturn(new User("Nombre", "Apellidos", email, "pass", null, null, true, false, null));

        User user = service.findUserByEmail(email);
        assertNotNull(user);
        assertEquals("Apellidos", user.getLastName());
        assertFalse(user.isAdmin());
    }

    /**
     * Try to confirm a User wich does not exist.
     */
    @Test(expected = IllegalArgumentException.class)
    public void confirmUserNotCreated() {
        String email = "user@domain.com";
        User newUser = new User("Nombre", "Apellidos", email, "pass", null, null, null, null, null);
        when(userDao.findByEmail(email)).thenReturn(null);

        service.confirmUser(newUser);
    }

    /**
     * Confirm an already created User account.
     */
    @Test
    public void confirmUserSuccessfully() {
        String email = "user@domain.com";
        User user = new User("Nombre", "Apellidos", email, "pass", null, null, null, null, null);
        when(userDao.findByEmail(email)).thenReturn(user);

        service.confirmUser(user);
        assertTrue(user.isActive());
    }

    /**
     * Try to find a Verification instance wich not exist.
     */
    @Test
    public void findVerificationWrong() {
        String wrongToken = "asdf";
        when(validationDao.findByToken(wrongToken)).thenReturn(null);

        Verification verification = service.getVerification(wrongToken);
        assertNull(verification);
    }

    /**
     * Find The verification instance by the token String successfully.
     */
    @Test
    public void findVerificationSuccessfully() {
        String token = "tokenvalid123";
        when(validationDao.findByToken(token)).thenReturn(new Verification(token, new User()));

        Verification verification = service.getVerification(token);
        assertNotNull(verification);
        assertEquals(token, verification.getToken());
    }
}
