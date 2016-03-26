package busy.notifications;

import java.util.List;

/**
 * Notification persistence interface.
 * 
 * @author malkomich
 */
public interface NotificationDao {

    /**
     * Persists a new notification or updates an existing one.
     * 
     * @param notification
     *            the notification object to be persisted
     */
    void save(Notification notification);

    /**
     * Gets the list of all current notification objects attached to the user with the given userId.
     * 
     * @param userId
     *            unique ID of the user
     * @return The list of resultant notifications
     */
    List<Notification> findByUserId(int userId);

}
