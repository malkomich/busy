package busy.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import busy.notifications.Notification;
import busy.notifications.NotificationDao;
import busy.user.User;

/**
 * Notification service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public void setNotificationDao(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    /*
     * (non-Javadoc)
     * @see busy.notifications.NotificationService#saveNotifications(busy.
     * notifications.Notification)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveNotification(Notification notification) {

        notificationDao.save(notification);
    }

    /*
     * (non-Javadoc)
     * @see busy.notifications.NotificationService#findNotificationsByUser(busy.user. User)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Notification> findNotificationsByUser(User user) {

        return notificationDao.findByUser(user);
    }

}
