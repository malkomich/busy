package busy.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import busy.Application;

/**
 * Test Case for the UserService class.
 * 
 * @author malkomich
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceTest {

	@Autowired
	private UserServiceImpl service;

	private UserDao userDao;

	@Before
	public void setUp() {
		userDao = mock(UserDao.class);
		service.setUserDao(userDao);
	}

	/**
	 * Try to find a user whose email does not exists in the database.
	 */
	@Test
	public void findUserWrong() {
		String email = "asdf@busy.com";
		when(userDao.findByEmail(email)).thenReturn(null);

		User user = service.findUserByEmail(email);
		assertNull(user);
	}

	/**
	 * Find a user whose email exists in the database.
	 */
	@Test
	public void findUserSuccess() {
		String email = "user@domain.com";
		when(userDao.findByEmail(email)).thenReturn(
				new User("Nombre", "Apellidos", email, "pass", null, null, true, false, null));

		User user = service.findUserByEmail(email);
		assertNotNull(user);
		assertEquals("Apellidos", user.getLastName());
		assertFalse(user.isAdmin());
	}
}
