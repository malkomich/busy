package busy;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import busy.notifications.Notification;
import busy.notifications.NotificationService;
import busy.user.User;

@Controller
@SessionAttributes(value = {MainController.NOTIFICATIONS_SESSION})
public class MainController {

	/**
	 * Spring Model Attributes.
	 */
	static final String USER_SESSION = "user";
	static final String LOGIN_REQUEST = "loginForm";
	
	static final String USERNAME = "username";
	static final String NOTIFICATIONS_SESSION = "notifications";
	
	/**
	 * URL Paths.
	 */
	private static final String PATH_USER = "/{username}";
	
	/**
	 * JSP's
	 */
	private static final String MAIN_PAGE = "main";
	
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * Shows the login page in case the user is not identified yet, otherwise
	 * the main page for the logged user will be shown.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value=PATH_USER)
	public String index(@PathVariable() String username, HttpSession session, Model model) {

		model.addAttribute(USERNAME, username);
		User user = (User) session.getAttribute(USER_SESSION);
		
		List<Notification> notifications = notificationService.findNotificationsByUser(user);
		model.addAttribute(NOTIFICATIONS_SESSION, notifications);
		
		return MAIN_PAGE;
	}
}
