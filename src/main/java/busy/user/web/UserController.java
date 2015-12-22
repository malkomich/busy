package busy.user.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import busy.user.User;
import busy.user.UserService;

/**
 * Controller for User operations, like Login or Register.
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

	/**
	 * URL Paths.
	 */
	private static final String PATH_ROOT = "/";

	/**
	 * JSP's
	 */
	private static final String LOGIN_PAGE = "login";
	private static final String MAIN_PAGE = "main";

	@Autowired
	private UserService userService;

	private User user;

	@RequestMapping(value = PATH_ROOT, method = RequestMethod.GET)
	public String index(Model model) {

		if (model.containsAttribute(USER_SESSION)
				&& ((User) model.asMap().get(USER_SESSION) != null))
			return MAIN_PAGE;

		LoginForm loginForm = new LoginForm();
		model.addAttribute(LOGIN_REQUEST, loginForm);
		return LOGIN_PAGE;
	}

	@RequestMapping(value = PATH_ROOT, method = RequestMethod.POST)
	public String login(@ModelAttribute(LOGIN_REQUEST) @Valid LoginForm loginForm,
			BindingResult result, Model model) {

		LoginValidator validator = new LoginValidator();
		validator.validate(loginForm, result);

		if (result.hasErrors()) {
			return LOGIN_PAGE;
		}

		model.addAttribute(USER_SESSION, user);
		return MAIN_PAGE;
	}
	
	/**
	 * Login Form Validator. It validate if email exists, and if the user with
	 * this email have the given password.
	 * 
	 * @author malkomich
	 *
	 */
	private class LoginValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			
			return LoginForm.class.equals(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {

			LoginForm loginForm = (LoginForm) target;
			user = userService.findUserByEmail(loginForm.getEmail());

			if (user == null) {
				
				errors.rejectValue("email", "email.wrong");
				
			} else if (!loginForm.getPassword().equals(user.getPassword())) {
				
				errors.rejectValue("password", "password.wrong");
			}
		}
	}

}
