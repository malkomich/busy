package busy.user.web;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class LoginForm {

	@Email
	@NotNull
	private String email;
	
	@NotNull
	private String pasword;

	public LoginForm() {

	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.pasword = password;
	}

	public String getPassword() {
		return pasword;
	}

}
