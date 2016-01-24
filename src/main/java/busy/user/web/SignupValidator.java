package busy.user.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.user.User;
import busy.user.UserService;

/**
 * Register Form Validator. It validates if all fields for the new account are
 * valid and checks all the unique keys.
 * 
 * @author malkomich
 *
 */
public class SignupValidator implements Validator {

	private UserService userService;
	
	public SignupValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SignupValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SignupForm registerForm = (SignupForm) target;
		
		User user = userService.findUserByEmail(registerForm.getEmail());
		
		if(user != null)
			errors.rejectValue("email", "email.exists");
		
		if(!registerForm.getPassword().equals(registerForm.getConfirmedPassword())) {
			errors.rejectValue("confirmedPassword", "confirmed_password.not_match");
		}

	}

}
