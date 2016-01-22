package busy.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.user.User;
import busy.user.UserService;

/**
 * Login Form Validator. It validates if email exists, and if the user with
 * this email has the given password.
 * 
 * @author malkomich
 *
 */
@Component
public class LoginValidator implements Validator {

	private User user;
	
	@Autowired
	private UserService userService;
	
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
	
	public User getUser() {
		return user;
	}
}
