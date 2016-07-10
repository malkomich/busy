package busy.notifications;

import java.util.List;

import busy.user.User;

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
     * @param user
     *            user which notifications are requested
     * @return The list of resultant notifications
     */
    List<Notification> findByUser(User user);

    /**
     * Gets a specific notification given his ID.
     * 
     * @param id
     *            unique ID of the notification
     * @return The resultant notification
     */
    Notification findById(int id);

    /**
     * Update read status for all notifications attached to the given user.
     * 
     * @param read
     *            value of the read property. True if the notification has been read
     * @param user
     *            Owner of the notifications to update
     */
    void updateReadStatus(boolean read, User user);

}
