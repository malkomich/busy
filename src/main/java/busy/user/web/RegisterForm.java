package busy.user.web;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import busy.location.City;
import busy.location.Country;

public class RegisterForm {

	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@Email
	@NotNull
	private String email;
	
	private String nif;
	
	private Country country;
	
	private City city;
	
	private String zipCode;
	
	private String phone;
	
	@NotNull
	private String pasword;
	
	@NotNull
	private String confirmedPassword;

	public RegisterForm() {

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
