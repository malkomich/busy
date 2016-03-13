package busy.notifications;

import java.util.List;

import busy.user.User;

public interface NotificationService {

	/**
	 * Save a Notification item to the database.
	 * 
	 * @param notification
	 */
	void saveNotification(Notification notification);
	
	/**
	 * Obtains all the notifications related to the given User.
	 * 
	 * @param user
	 * @return
	 */
	List<Notification> findNotificationsByUser(User user);
}
