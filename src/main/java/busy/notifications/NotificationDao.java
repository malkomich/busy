package busy.notifications;

import java.util.List;

import busy.user.User;

public interface NotificationDao {

	void save(Notification notification);

	List<Notification> findByUserId(int userId);

}
