package busy.company.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import busy.notifications.Notification;
import busy.notifications.NotificationService;
import busy.notifications.messages.CompanyMsg;
import busy.user.User;

/**
 * Application listener called when a company registration is performed.
 * 
 * @author malkomich
 *
 */
@Component
public class RegisterCompanyListener implements ApplicationListener<OnRegisterCompany> {

    @Autowired
    private NotificationService service;

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

        // Add a new notification for the user
        User user = event.getManager().getUser();
        Notification notification = new Notification(Notification.Type.COMPANY, CompanyMsg.COMPANY_PENDING, user);

        service.saveNotification(notification);

    }
}
