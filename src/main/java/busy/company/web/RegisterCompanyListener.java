package busy.company.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import busy.notifications.Notification;
import busy.notifications.NotificationService;

/**
 * Application listener called when a company registration is performed.
 * 
 * @author malkomich
 *
 */
@Component
public class RegisterCompanyListener implements ApplicationListener<OnRegisterCompany> {

    private static final String NOTIFICATION_MSG_CODE = "notification.message.company_pending";

    @Autowired
    private NotificationService service;

    @Autowired
    private MessageSource messages;

    /**
     * Absolute path of the root URL, to let from now the use of relative URL paths.
     */
    @Value("${web.rootUrl}")
    private String rootUrl;

    @Override
    public void onApplicationEvent(OnRegisterCompany event) {
        this.confirmRegistration(event);
    }

    /**
     * Creates a new notification for the manager of the company.
     * 
     * @param event
     */
    private void confirmRegistration(OnRegisterCompany event) {

        int userId = event.getManager().getUser().getId();
        // Add a new notification for the user
        String notificationType = messages.getMessage(Notification.Type.COMPANY.getMsgCode(), null, event.getLocale());
        String notificationMessage = messages.getMessage(NOTIFICATION_MSG_CODE, null, event.getLocale());
        Notification notification = new Notification(userId, notificationType, notificationMessage);
        service.saveNotification(notification);

    }
}
