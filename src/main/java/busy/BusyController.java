package busy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Base Controller parent for the application.
 * 
 * @author malkomich
 *
 */
@Controller
@SessionAttributes(value = {BusyController.NOTIFICATIONS_SESSION, BusyController.ROLES_SESSION,
    BusyController.USER_SESSION, BusyController.USERNAME_SESSION, BusyController.ROLE_SESSION,
    BusyController.SERVICE_TYPES_SESSION, BusyController.BRANCH_ROLES_SESSION})
public abstract class BusyController {

    /*
     * Spring Model attributes
     */
    protected static final String NOTIFICATIONS_SESSION = "notifications";
    protected static final String ROLES_SESSION = "roleList";
    protected static final String USER_SESSION = "user";
    protected static final String USERNAME_SESSION = "username";

    protected static final String MESSAGE_REQUEST = "messageFromController";

    // Temporary session attributes (they must be request ones)
    protected static final String ROLE_SESSION = "role";
    protected static final String SERVICE_TYPES_SESSION = "serviceTypes";
    protected static final String BRANCH_ROLES_SESSION = "branchRoles";

    // Temporary global request attributes (a handler must be implemented)
    protected static final String LOGIN_REQUEST = "loginForm";

}
