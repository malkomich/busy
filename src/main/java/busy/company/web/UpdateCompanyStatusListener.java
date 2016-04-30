package busy.company.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import busy.company.Company;
import busy.notifications.Notification;
import busy.notifications.NotificationService;
import busy.notifications.messages.CompanyMsg;
import busy.util.UriUtils;

/**
 * Application listener called when an status update of a company is performed.
 * 
 * @author malkomich
 *
 */
@Component
public class UpdateCompanyStatusListener implements ApplicationListener<OnUpdateCompanyStatus> {

    private static final String EMAIL_SUBJECT_CODE = "new_notifications.email.subject";
    private static final String EMAIL_CONTENT_CODE = "new_notifications.email.content";

    @Autowired
    private NotificationService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnUpdateCompanyStatus event) {
        this.confirmUpdate(event);
    }

    /**
     * Creates a new notification for the manager of the company, and send him an email.
     * 
     * @param event
     */
    private void confirmUpdate(OnUpdateCompanyStatus event) {

        Company company = event.getCompany();

        // Add a new notification for the user
        Notification notification =
                new Notification(Notification.Type.COMPANY, CompanyMsg.COMPANY_APPROVED, event.getManager());
        service.saveNotification(notification);

        // Send an informative email
        String recipientAddress = company.getEmail();
        String subject = messages.getMessage(EMAIL_SUBJECT_CODE, null, event.getLocale());
        String message = messages.getMessage(EMAIL_CONTENT_CODE, null, event.getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        String rootPath = UriUtils.getRootPath(env);
        email.setText(message + rootPath);
        mailSender.send((SimpleMailMessage) email);

    }
}
