package busy.notifications;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.user.User;
import busy.util.SecureSetter;

@DatabaseSetup("../user/userSet.xml")
public class NotificationDBTest extends AbstractDBTest {

	@Autowired
	private NotificationyDaoImpl repository;
	
	private User user;
	
	@Before
	public void setUp() {
		
		user = new User();
		SecureSetter.setId(user, 1);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithUserNull() {
		
		Notification notification = new Notification();
		notification.setType("Empresa");
		notification.setMessage("Su empresa ha sido verificada");
		repository.save(notification);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithUserInvalid() {
		
		Notification notification = new Notification();
		User userInvalid = new User();
		SecureSetter.setId(userInvalid, INVALID_ID);
		notification.setUser(userInvalid);
		
		notification.setType("Empresa");
		notification.setMessage("Su empresa ha sido verificada");
		repository.save(notification);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithTypeNull() {
		
		Notification notification = new Notification();
		notification.setUser(user);
		notification.setMessage("Su empresa ha sido verificada");
		repository.save(notification);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithMessageNull() {
		
		Notification notification = new Notification();
		notification.setUser(user);
		notification.setType("Empresa");
		repository.save(notification);
	}
	
	@Test
	public void insertSuccessfully() {
		
		Notification notification = new Notification();
		notification.setUser(user);
		notification.setType("Empresa");
		notification.setMessage("Su empresa ha sido verificada");
		repository.save(notification);
	}
	
}
