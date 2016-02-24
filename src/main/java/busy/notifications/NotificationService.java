package busy.notifications;

import java.util.List;

import busy.user.User;

public interface NotificationService {

	/**
	 * Obtains all the notifications related to the given User.
	 * 
	 * @param user
	 * @return
	 */
	List<Notification> findNotificationsByUser(User user);
}
