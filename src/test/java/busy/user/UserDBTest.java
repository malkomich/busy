package busy.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.location.Address;
import busy.util.SecureSetter;

/**
 * Test Case for the UserDao implementation class.
 * 
 * @author malkomich
 *
 */
public class UserDBTest extends AbstractDBTest {

	@Autowired
	protected UserDaoImpl repository;

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithNameNull() {

		User user = new User();
		user.setLastName("González Cabrero");
		user.setEmail("malkomich@gmail.com");
		user.setPassword("123456");
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithSurnameNull() {

		User user = new User();
		user.setFirstName("Juan Carlos");
		user.setEmail("malkomich@gmail.com");
		user.setPassword("123456");
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithEmailNull() {

		User user = new User();
		user.setFirstName("Juan Carlos");
		user.setLastName("González Cabrero");
		user.setPassword("123456");
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithPasswordNull() {

		User user = new User();
		user.setFirstName("Juan Carlos");
		user.setLastName("González Cabrero");
		user.setEmail("malkomich@gmail.com");
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithNameTooLong() {

		User user = new User("aaaaabbbbbcccccdddddeeeeefffffgggggh", "González Cabrero",
				"malkomich@gmail.com", "123456", null, null, null, null, null);
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithSurnameTooLong() {

		User user = new User("Juan Carlos", "aaaaabbbbbcccccdddddeeeeefffffgggggh",
				"malkomich@gmail.com", "123456", null, null, null, null, null);
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithEmailTooLong() {

		User user = new User("Juan Carlos", "González Cabrero",
				"aaaaabbbbbcccccdddddeeeeefffffggggghhhhhi@gmail.com", "123456", null, null, null,
				null, null);
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithNifTooLong() {

		User user = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"711526341B", null, null, null, null);
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithPhoneTooLong() {

		User user = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"71152634B", "1231231231231", null, null, null);
		repository.save(user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithEmailDuplicated() {

		User user1 = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"71152639B", null, null, null, null);
		User user2 = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"71152634B", null, null, null, null);
		repository.save(user1);
		repository.save(user2);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithNifDuplicated() {

		User user1 = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"71152634B", null, null, null, null);
		User user2 = new User("Juan Carlos", "González Cabrero", "diferente@gmail.com", "123456",
				"71152634B", null, null, null, null);
		repository.save(user1);
		repository.save(user2);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void insertWithAddressNotExists() {

		Address address = new Address();
		User user = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"71152634B", null, null, null, address);
		repository.save(user);
	}

	@Test
	public void insertUserWithoutAddressSuccesfully() {

		User user = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"71121212D", "902202122", null, null, null);
		repository.save(user);
		assertTrue(user.getId() > 0);
		
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
		assertFalse(resultuser.isActive());
		assertFalse(resultuser.isAdmin());
	}

	@Test
	@DatabaseSetup("../location/countrySet.xml")
	@DatabaseSetup("../location/citySet.xml")
	@DatabaseSetup("../location/addressSet.xml")
	public void insertUserWithAddressSuccesfully() {

		Address address = new Address();
		SecureSetter.setId(address, 1);
		
		User user = new User("Juan Carlos", "González Cabrero", "malkomich@gmail.com", "123456",
				"71121212D", "902202122", null, null, address);
		repository.save(user);
		assertTrue(user.getId() > 0);
		
		Iterator<User> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		User resultuser = it.next();

		assertEquals("Juan Carlos", resultuser.getFirstName());
		assertEquals("González Cabrero", resultuser.getLastName());
		assertEquals("malkomich@gmail.com", resultuser.getEmail());
		assertEquals("123456", resultuser.getPassword());
		assertEquals("71121212D", resultuser.getNif());

		assertNotNull(resultuser.getAddress());
		assertTrue(resultuser.getAddress().getAddress1().contains("Calle Mayor"));

		assertEquals("902202122", resultuser.getPhone());
		assertFalse(resultuser.isActive());
		assertFalse(resultuser.isAdmin());
	}

	@Test
	@DatabaseSetup("userSet.xml")
	public void findUserByEmailWrong() {

		User user = repository.findByEmail("asdf@busy.com");
		assertNull(user);
	}

	@Test
	@DatabaseSetup("userSet.xml")
	public void findUserByEmailSuccesfully() {

		User user = repository.findByEmail("user@domain.com");
		assertNotNull(user);
		assertEquals("Apellidos", user.getLastName());
		assertFalse(user.isAdmin());
	}
	
	@Test
	public void findAllUsersWhenEmpty() {
		
		List<User> userList = repository.findAll();
		assertTrue(userList.isEmpty());
	}
	
}
