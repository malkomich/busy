package busy.user.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import busy.location.Address;
import busy.location.City;
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
@SessionAttributes(UserController.USER_SESSION)
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
	private static final String PATH_SIGNUP = "signup";
	private static final String PATH_SIGNUP_CITIES_UPDATE = "get_city_list";
	private static final String PATH_EMAIL_CONFIRM = "verificate_email";

	/**
	 * JSP's
	 */
	private static final String LOGIN_PAGE = "login";
	private static final String MAIN_PAGE = "main";
	private static final String SIGNUP_PAGE = "signup";

	@Autowired
	private UserService userService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	/**
	 * Shows the login page in case the user is not identified yet, otherwise
	 * the main page for the logged user will be shown.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = PATH_ROOT, method = RequestMethod.GET)
	public String index(Model model) {

		if (model.containsAttribute(USER_SESSION) && ((User) model.asMap().get(USER_SESSION) != null))
			return MAIN_PAGE;

		LoginForm loginForm = new LoginForm();
		model.addAttribute(LOGIN_REQUEST, loginForm);

		return LOGIN_PAGE;
	}

	/**
	 * Process the login form and validates it.
	 * 
	 * @param loginForm
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = PATH_ROOT, method = RequestMethod.POST)
	public String login(@ModelAttribute(LOGIN_REQUEST) @Valid LoginForm form, BindingResult result, Model model) {

		LoginValidator validator = new LoginValidator(userService);

		validator.validate(form, result);

		if (result.hasErrors()) {
			return LOGIN_PAGE;
		}

		User user = userService.findUserByEmail(form.getEmail());
		model.addAttribute(USER_SESSION, user);

		return MAIN_PAGE;
	}

	/**
	 * Shows the signup form.
	 * 
	 * @param model
	 * @return
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

		eventPublisher
				.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), request.getContextPath()));

		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + SIGNED_UP_REQUEST,
				result);
		redirectAttributes.addFlashAttribute(SIGNED_UP_REQUEST, true);

		return "redirect:" + PATH_ROOT;
	}

	@RequestMapping(value = PATH_SIGNUP_CITIES_UPDATE, method = RequestMethod.GET)
	public @ResponseBody List<City> updateCities(
			@RequestParam(value = "countryCode", required = true) String countryCode, ModelMap modelMap) {

		return locationService.findCitiesByCountryCode(countryCode);
	}

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

		return "redirect:" + PATH_ROOT;
	}

}
