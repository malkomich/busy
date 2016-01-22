package busy.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.user.UserService;

/**
 * Register Form Validator. It validates if all fields for the new account are
 * valid and checks all the unique keys.
 * 
 * @author malkomich
 *
 */
@Component
public class SignupValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return SignupValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SignupForm registerForm = (SignupForm) target;

	}

}
