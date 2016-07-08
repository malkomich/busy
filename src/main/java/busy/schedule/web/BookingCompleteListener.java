package busy.schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import busy.notifications.Notification;
import busy.notifications.NotificationService;
import busy.notifications.messages.BookingMsg;
import busy.user.User;

/**
 * Application listener called when a booking is performed.
 * 
 * @author malkomich
 *
 */
@Component
public class BookingCompleteListener implements ApplicationListener<OnBookingComplete> {

    @Autowired
    private NotificationService service;

    @Override
    public void onApplicationEvent(OnBookingComplete event) {

        this.confirmBooking(event);
    }

    /**
     * Creates a new notification for the user which booked the scheduled time slot.
     * 
     * @param event
     */
    private void confirmBooking(OnBookingComplete event) {

        // Add a new notification for the user
        User user = event.getUser();
        Notification notification = new Notification(Notification.Type.BOOKING, BookingMsg.BOOKING_SUCCESS, user);

        service.saveNotification(notification);

    }

}
