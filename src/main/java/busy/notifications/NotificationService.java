package busy.notifications;

import java.util.List;

import busy.user.User;

/**
 * Notification service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface NotificationService {

    /**
     * Saves or updates a notification.
     * 
     * @param notification
     *            The notification to be saved
     */
    void saveNotification(Notification notification);

    /**
     * Gets all the notifications related to the given user.
     * 
     * @param user
     *            the user attached to the resultant notifications
     * @return The list of resultant notifications
     */
    List<Notification> findNotificationsByUser(User user);

    /**
     * Gets a specific notification given his ID.
     * 
     * @param id
     *            unique ID of the notification
     * @return The resultant notification
     */
    Notification findNotificationById(int id);

    /**
     * Mark all the notifications attached to the given user.
     * 
     * @param user
     *            Owner of the notifications to update
     */
    void setNotificationsAsRead(User user);
}
