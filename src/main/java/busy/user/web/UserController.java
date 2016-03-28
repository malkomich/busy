package busy.user.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import busy.location.Address;
import busy.location.Country;
import busy.location.LocationService;
import busy.user.User;
import busy.user.UserService;
import busy.user.Verification;

/**
 * Controller for User operations, like Login or Sign up.
 * 
 * @author malkomich
 *
 */
@Controller
@SessionAttributes(value = { UserController.USER_SESSION })
public class UserController {

    /**
     * Spring Model Attributes.
     */
    static final String USER_SESSION = "user";
    static final String LOGIN_REQUEST = "loginForm";
    static final String SIGNUP_REQUEST = "signupForm";

    static final String COUNTRY_ITEMS_REQUEST = "countryItems";
    static final String SIGNED_UP_REQUEST = "signedUp";
    static final String VALIDATION_MSG_REQUEST = "tokenValid";

    /**
     * URL Paths.
     */
    private static final String PATH_ROOT = "/";
    private static final String PATH_LOGIN = "/login";
    private static final String PATH_SIGNUP = "/signup";
    private static final String PATH_EMAIL_CONFIRM = "/verificate_email";
    private static final String PATH_LOGOUT = "/logout";
    private static final String PATH_ADMIN = "/admin";

    /**
     * JSP's
     */
    private static final String LOGIN_PAGE = "login";
    private static final String SIGNUP_PAGE = "signup";

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Shows the login page.
     * 
     * @param model
     *            Spring model instance
     * @return The login page
     */
    @RequestMapping(value = PATH_LOGIN, method = RequestMethod.GET)
    public String index(Model model) {

        LoginForm loginForm = new LoginForm();
        model.addAttribute(LOGIN_REQUEST, loginForm);

        return LOGIN_PAGE;
    }

    /**
     * Process the login form and validates it. If the user has been successfully validated, a
     * redirect to root path or admin path (if the user is admin) is requested, otherwise the login
     * page is shown again.
     * 
     * @param form
     *            HTTP input request
     * @param result
     *            form result status
     * @param model
     *            Spring model instance
     * @param redirectAttributes
     *            model attributes storage to be propagated in a redirection
     * 
     * @return The redirect request, or the login page on form error
     */
    @RequestMapping(value = PATH_LOGIN, method = RequestMethod.POST)
    public String login(@ModelAttribute(LOGIN_REQUEST) @Valid LoginForm form, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        LoginValidator validator = new LoginValidator(userService);

        validator.validate(form, result);

        if (result.hasErrors()) {
            return LOGIN_PAGE;
        }

        User user = userService.findUserByEmail(form.getEmail());
        model.addAttribute(USER_SESSION, user);

        return (user.isAdmin()) ? "redirect:" + PATH_ADMIN : "redirect:" + PATH_ROOT;
    }

    /**
     * Shows the sign up form.
     * 
     * @param model
     *            Spring model instance
     * @return The sign up page
     */
    @RequestMapping(value = PATH_SIGNUP, method = RequestMethod.GET)
    public String showSignup(Model model) {

        if (!model.containsAttribute(SIGNUP_REQUEST))
            model.addAttribute(SIGNUP_REQUEST, new SignupForm());

        // Add input selection items to the model.
        Map<String, String> countryItems = new LinkedHashMap<String, String>();
        for (Country country : locationService.findCountries()) {
            countryItems.put(country.getCode(), country.getName());
        }
        model.addAttribute(COUNTRY_ITEMS_REQUEST, countryItems);

        return SIGNUP_PAGE;
    }

    /**
     * New user sign up request, which will try to create a new account.
     * 
     * @param form
     *            HTTP input request
     * @param result
     *            form result status
     * @param redirectAttributes
     *            model attributes storage to be propagated in a redirection
     * @param request
     *            Web request interface
     * @param model
     *            Spring model instance
     * @return The redirect request to login path
     */
    @RequestMapping(value = PATH_SIGNUP, method = RequestMethod.POST)
    public String signup(@ModelAttribute(SIGNUP_REQUEST) @Valid SignupForm form, BindingResult result,
            RedirectAttributes redirectAttributes, WebRequest request, Model model) {

        SignupValidator validator = new SignupValidator(userService);

        validator.validate(form, result);

        if (result.hasErrors()) {
            return showSignup(model);
        }

        // Save the new User data.
        Address address = new Address();
        address.setZipCode(form.getZipCode());
        address.setCity(locationService.findCityById(Integer.parseInt(form.getCityId())));
        locationService.saveAddress(address);

        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setPassword(form.getPassword());
        user.setAddress(address);
        userService.saveUser(user);

        eventPublisher.publishEvent(new OnSignUpCompleteEvent(user, request.getLocale(), request.getContextPath()));

        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + SIGNED_UP_REQUEST,
                result);
        redirectAttributes.addFlashAttribute(SIGNED_UP_REQUEST, true);

        return "redirect:" + PATH_LOGIN;
    }

    /**
     * Account validation request for a unique token given.
     * 
     * @param model
     *            Spring model instance
     * @param token
     *            unique String token
     * @param redirectAttributes
     *            model attributes storage to be propagated in a redirection
     * @return The redirect request to login path
     */
    @RequestMapping(value = PATH_EMAIL_CONFIRM, method = RequestMethod.GET)
    public String emailConfirm(Model model, @RequestParam("token") String token,
            RedirectAttributes redirectAttributes) {

        boolean tokenValid = false;

        Verification verificationToken = userService.getVerification(token);
        if (verificationToken != null) {

            User user = verificationToken.getUser();

            userService.confirmUser(user);

            tokenValid = true;
        }

        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + VALIDATION_MSG_REQUEST,
                null);
        redirectAttributes.addFlashAttribute(VALIDATION_MSG_REQUEST, tokenValid);

        return "redirect:" + PATH_LOGIN;
    }

    /**
     * Invalidate the session for the current user.
     * 
     * @param model
     *            Spring model instance
     * @param status
     *            session instance
     * @return The redirect request to login path
     */
    @RequestMapping(value = PATH_LOGOUT, method = RequestMethod.GET)
    public String logout(Model model, SessionStatus status) {

        status.setComplete();

        return "redirect:" + PATH_LOGIN;
    }

}
