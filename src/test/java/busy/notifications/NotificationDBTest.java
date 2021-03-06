package busy.notifications;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.notifications.messages.CompanyMsg;
import busy.user.User;

@DatabaseSetup("../user/userSet.xml")
public class NotificationDBTest extends AbstractDBTest {

    @Autowired
    private NotificationDaoImpl repository;

    private User user;

    @Before
    public void setUp() {

        user = new User();
        user.setId(1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithUserNull() {
        Notification notification = new Notification();
        notification.setType(Notification.Type.COMPANY);
        notification.setMessage(CompanyMsg.COMPANY_PENDING);
        repository.save(notification);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithUserInvalid() {

        Notification notification = new Notification();

        user.setId(INVALID_ID);
        notification.setUser(user);

        notification.setType(Notification.Type.COMPANY);
        notification.setMessage(CompanyMsg.COMPANY_PENDING);
        repository.save(notification);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithTypeNull() {

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(CompanyMsg.COMPANY_PENDING);
        repository.save(notification);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithMessageNull() {

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(Notification.Type.COMPANY);
        repository.save(notification);
    }

    @Test
    public void insertSuccessfully() {

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(Notification.Type.COMPANY);
        notification.setMessage(CompanyMsg.COMPANY_PENDING);
        repository.save(notification);
    }

    @Test
    @DatabaseSetup("../notification/notificationSet.xml")
    public void markAsRead() {
        Notification notification = repository.findById(1);
        assertFalse(notification.isRead());
        notification.setRead(true);
        repository.save(notification);
        notification = repository.findById(1);
        assertTrue(notification.isRead());
    }
    
    @Test
    @DatabaseSetup("../notification/notificationSet.xml")
    public void markAllAsRead() {
        repository.updateReadStatus(true, user);
        
        Notification notification = repository.findById(1);
        assertTrue(notification.isRead());
        notification = repository.findById(2);
        assertTrue(notification.isRead());
    }

}
