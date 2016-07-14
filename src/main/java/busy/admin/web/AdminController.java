package busy.admin.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import busy.company.CompanyService;
import busy.notifications.Notification;
import busy.notifications.NotificationService;
import busy.user.User;
import busy.user.UserService;

/**
 * Controller for the admin zone requests
 * 
 * @author malkomich
 */
@Controller
@SessionAttributes(
    value = {AdminController.NOTIFICATIONS_SESSION, AdminController.USER_SESSION, AdminController.USERNAME_SESSION})
public class AdminController {

    /**
     * Spring Model Attributes.
     */
    static final String USER_SESSION = "user";
    static final String USERNAME_SESSION = "username";
    static final String NOTIFICATIONS_SESSION = "notifications";

    static final String MESSAGE_REQUEST = "messageFromController";
    static final String COMPANIES_NUM_REQUEST = "numOfcompanies";
    static final String USERS_NUM_REQUEST = "numOfUsers";
    static final String USERS_REQUEST = "userList";

    /**
     * URL Paths.
     */
    private static final String PATH_ROOT = "/";
    private static final String PATH_ADMIN = "/admin";
    private static final String PATH_TOOGLE_USER = "/admin/toogle-user";
    private static final String PATH_USERS_UPDATE = "/get_user_list";

    /**
     * JSP's
     */
    private static final String ADMIN_PAGE = "admin";
    private static final String USERS_FRAGMENT = "admin-users";

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Shows the admin page in case of the user has log in as admin, or does a redirect request to
     * root path in other case
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
            
            int numOfUsers = userService.countAllUsers();
            model.addAttribute(USERS_NUM_REQUEST, numOfUsers);

            return ADMIN_PAGE;
        }

        return "redirect:" + PATH_ROOT;

    }

    /**
     * Request to find all the current users available.
     * 
     * @param model
     *            Spring model instance
     * @return The fragment with the updated list of users
     */
    @RequestMapping(value = PATH_USERS_UPDATE, method = RequestMethod.GET)
    public String updateUsers(Model model) {

        List<User> userList = userService.findAll();
        model.addAttribute(USERS_REQUEST, userList);
        
        return USERS_FRAGMENT;
    }

    /**
     * [POST] Request to change the active status of a user.
     * 
     * @param userId
     *            unique ID of the user
     * @param active
     *            active status
     * @param model
     *            Spring model instance
     * @return The fragment of the user list
     */
    @RequestMapping(value = PATH_TOOGLE_USER, method = RequestMethod.POST)
    public String toogleUserActiveStatus(@RequestParam(value = "id", required = true) String userId,
        @RequestParam(value = "active", required = true) boolean active, Model model) {

        userService.toogleUserActiveStatus(Integer.parseInt(userId), active);
        model.addAttribute(USERS_REQUEST, userService.findAll());

        return USERS_FRAGMENT;
    }

}
