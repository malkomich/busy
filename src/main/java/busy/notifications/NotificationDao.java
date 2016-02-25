package busy.notifications;

import java.util.List;

public interface NotificationDao {

	void save(Notification notification);

	List<Notification> findByUserId(int userId);

}
