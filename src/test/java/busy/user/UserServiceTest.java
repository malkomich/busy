package busy.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

	/**
	 * Try to find a user whose email does not exists in the database.
	 */
	@Test(expected = Exception.class)
	public void findUserWrong() {
		User user = service.findUserByEmail("asdf@busy.com");
		assertNull(user);
	}

	/**
	 * Find a user whose email exists in the database.
	 */
	@Test
	public void findUserSuccess() {
		User user = service.findUserByEmail("admin@busy.com");
		assertNotNull(user);
		assertEquals("García", user.getLastName());
		assertTrue(user.isAdmin());
	}
}
