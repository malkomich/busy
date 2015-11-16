package busy.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.location.Address;

/**
 * Test Case for the UserDao interface.
 * 
 * @author malkomich
 *
 */
public class UserDBTest extends AbstractDBTest {

	@Autowired
	protected UserDaoImpl repository;

	@Test(expected = Exception.class)
	public void nameNull() {
		User user = new User();
		user.setLastName("González Cabrero");
		user.setEmail("malkomich@gmail.com");
		user.setPassword("123456");
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void surnameNull() {
		User user = new User();
		user.setFirstName("Juan Carlos");
		user.setEmail("malkomich@gmail.com");
		user.setPassword("123456");
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void emailNull() {
		User user = new User();
		user.setFirstName("Juan Carlos");
		user.setLastName("González Cabrero");
		user.setPassword("123456");
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void passwordNull() {
		User user = new User();
		user.setFirstName("Juan Carlos");
		user.setLastName("González Cabrero");
		user.setEmail("malkomich@gmail.com");
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void nameTooLong() {
		User user = new User("aaaaabbbbbcccccdddddeeeeefffffgggggh",
				"González Cabrero", "malkomich@gmail.com", "123456", null, null,
				null, null, null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void surnameTooLong() {
		User user = new User("Juan Carlos",
				"aaaaabbbbbcccccdddddeeeeefffffgggggh", "malkomich@gmail.com",
				"123456", null, null, null, null, null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void emailTooLong() {
		User user = new User("Juan Carlos", "González Cabrero",
				"aaaaabbbbbcccccdddddeeeeefffffggggghhhhhi@gmail.com", "123456",
				null, null, null, null, null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void passwordTooShort() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "12345", null, null, null, null, null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void nifTooShort() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "7115262D", null, null, null,
				null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void nifTooLong() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "711526341B", null, null, null,
				null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void phoneTooShort() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71152624D", null, "123", null,
				null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void phoneTooLong() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71152634B", null,
				"1231231231231", null, null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void emailDuplicated() {
		User user1 = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71152639B", null, null, null,
				null);
		User user2 = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71152634B", null, null, null,
				null);
		repository.save(user1);
		repository.save(user2);
	}

	@Test(expected = Exception.class)
	public void nifDuplicated() {
		User user1 = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71152634B", null, null, null,
				null);
		User user2 = new User("Juan Carlos", "González Cabrero",
				"diferente@gmail.com", "123456", "71152634B", null, null, null,
				null);
		repository.save(user1);
		repository.save(user2);
	}

	@Test(expected = Exception.class)
	public void nameInvalidCharacters() {
		User user = new User("Juan & Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71152634B", null, null, null,
				null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void surnameInvalidCharacters() {
		User user = new User("Juan Carlos", "González 5 Cabrero",
				"malkomich@gmail.com", "123456", "71152634B", null, null, null,
				null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void invalidEmail() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomichgmail.com", "123456", "71152634B", null, null, null,
				null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void invalidNif() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "B17115263", null, null, null,
				null);
		repository.save(user);
	}

	@Test(expected = Exception.class)
	public void addressNotExists() {
		Address address = new Address();
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71152634B", address, null,
				null, null);
		repository.save(user);
	}

	@Test
	public void insertUserSuccesfully() {
		User user = new User("Juan Carlos", "González Cabrero",
				"malkomich@gmail.com", "123456", "71121212D", null, "902202122",
				null, null);
		repository.save(user);
		Iterator<User> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		User resultuser = it.next();
		assertEquals("Juan Carlos", resultuser.getFirstName());
		assertEquals("González Cabrero", resultuser.getLastName());
		assertEquals("malkomich@gmail.com", resultuser.getEmail());
		assertEquals("123456", resultuser.getPassword());
		assertEquals("71121212D", resultuser.getNif());
		assertNull(resultuser.getAddress());
		assertEquals("902202122", resultuser.getPhone());
		assertTrue(resultuser.isActive());
		assertFalse(resultuser.isAdmin());
	}

	@Test(expected = Exception.class)
	@DatabaseSetup("userSet.xml")
	public void findUserByEmailWrong() {
		User user = repository.findByEmail("asdf@busy.com");
		assertNull(user);
	}

	@Test
	@DatabaseSetup("userSet.xml")
	public void findUserByEmailSuccesfully() {
		User user = repository.findByEmail("admin@busy.com");
		assertNotNull(user);
		assertEquals("García", user.getLastName());
		assertTrue(user.isAdmin());
	}

}
