package busy.user.web;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import busy.location.City;
import busy.location.Country;

public class SignupForm {

	// FIELDS
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@Email
	@NotNull
	private String email;
	
	private Country country;
	
	private City city;
	
	private String zipCode;
	
	private String phone;
	
	@NotNull
	private String pasword;
	
	@NotNull
	private String confirmedPassword;

	// GETTERS AND SETTERS
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPassword(String password) {
		this.pasword = password;
	}

	public String getPassword() {
		return pasword;
	}
	
	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}
	
}
