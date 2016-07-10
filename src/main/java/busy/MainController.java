package busy;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import busy.notifications.Notification;
import busy.notifications.NotificationService;
import busy.role.Role;
import busy.role.RoleService;
import busy.user.User;
import busy.user.web.LoginForm;

/**
 * Controller for the root path, dispatching any more specific request to the right controller.
 * 
 * @author malkomich
 *
 */
@Controller
public class MainController extends BusyController {

    /**
     * URL Paths.
     */
    private static final String PATH_LOGIN = "/login";
    private static final String PATH_ADMIN = "/admin";
    private static final String PATH_NOTIFICATION_READ = "/notification/read";
    private static final String PATH_NOTIFICATION_READ_ALL = "/notification/read/all";

    /**
     * JSP's
     */
    private static final String MAIN_PAGE = "main";

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RoleService roleService;

    /**
     * Requests a login path redirect in case the user is not identified yet, otherwise the main
     * page for logged users or an admin path redirect will be requested.
     * 
     * @param model
     *            Spring model instance
     * @param session
     *            session instance
     * @return The main page, or the right redirect request
     */
    @RequestMapping(value = PATH_ROOT, method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {

        // Assures that no role is selected
        if(model.containsAttribute(ROLE_SESSION)) {

            model.asMap().remove(ROLE_SESSION);
        }

        if (model.containsAttribute(USER_SESSION) && ((User) model.asMap().get(USER_SESSION) != null)) {

            User user = (User) model.asMap().get(USER_SESSION);

            if (user.isAdmin()) {
                return "redirect:" + PATH_ADMIN;
            }

            String username = user.getEmail().split("@")[0];
            model.addAttribute(USERNAME_SESSION, username);

            List<Notification> notifications = notificationService.findNotificationsByUser(user);
            model.addAttribute(NOTIFICATIONS_SESSION, notifications);

            List<Role> roles = roleService.findRolesByUser(user);
            model.addAttribute(ROLES_SESSION, roles);

            return MAIN_PAGE;
        }

        LoginForm loginForm = new LoginForm();
        model.addAttribute(LOGIN_REQUEST, loginForm);

        return "redirect:" + PATH_LOGIN;
    }
    
    @RequestMapping(value = PATH_NOTIFICATION_READ, method = RequestMethod.POST)
    public boolean readNotification(@RequestParam(value = "id", required = true) String notificationId, Model model) {
        Notification notification = notificationService.findNotificationById(Integer.parseInt(notificationId));
        notification.setRead(true);
        notificationService.saveNotification(notification);
        return true;
    }
    
    @RequestMapping(value = PATH_NOTIFICATION_READ_ALL, method = RequestMethod.POST)
    public boolean readAllNotifications(Model model) {
        
        User user = (User) model.asMap().get(USER_SESSION);
        notificationService.setNotificationsAsRead(user);
        return true;
    }

}
