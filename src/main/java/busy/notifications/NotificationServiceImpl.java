package busy.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import busy.notifications.Notification;
import busy.notifications.NotificationDao;
import busy.user.User;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDao notificationDao;

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	@Override
	public List<Notification> findNotificationsByUser(User user) {

		return notificationDao.findByUserId(user.getId());
	}

}
