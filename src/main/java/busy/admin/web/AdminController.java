package busy.admin.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import busy.company.CompanyService;
import busy.notifications.Notification;
import busy.notifications.NotificationService;
import busy.user.User;

/**
 * Controller for the admin zone requests
 * 
 * @author malkomich
 */
@Controller
@SessionAttributes(value = { AdminController.NOTIFICATIONS_SESSION, AdminController.USER_SESSION,
    AdminController.USERNAME_SESSION })
public class AdminController {

    /**
     * Spring Model Attributes.
     */
    static final String USER_SESSION = "user";
    static final String USERNAME_SESSION = "username";
    static final String NOTIFICATIONS_SESSION = "notifications";

    static final String MESSAGE_REQUEST = "messageFromController";
    static final String COMPANIES_NUM_REQUEST = "numOfcompanies";

    /**
     * URL Paths.
     */
    private static final String PATH_ROOT = "/";
    private static final String PATH_ADMIN = "/admin";

    /**
     * JSP's
     */
    private static final String ADMIN_PAGE = "admin";

    @Autowired
    private CompanyService companyService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Shows the admin page in case of the user has log in as admin, or does a
     * redirect request to root path in other case
     * 
     * @param model
     *            Spring model instance
     * @param session
     *            session instance
     * @return The admin page or redirect request to root path
     */
    @RequestMapping(value = PATH_ADMIN, method = RequestMethod.GET)
    public String showAdminZone(Model model, HttpSession session) {

        if (model.containsAttribute(USER_SESSION) && ((User) model.asMap().get(USER_SESSION) != null)) {

            User user = (User) model.asMap().get(USER_SESSION);
            String username = user.getEmail().split("@")[0];
            model.addAttribute(USERNAME_SESSION, username);

            List<Notification> notifications = notificationService.findNotificationsByUser(user);
            model.addAttribute(NOTIFICATIONS_SESSION, notifications);

            int numOfCompanies = companyService.countAllCompanies();
            model.addAttribute(COMPANIES_NUM_REQUEST, numOfCompanies);

            return ADMIN_PAGE;
        }

        return "redirect:" + PATH_ROOT;

    }

}
