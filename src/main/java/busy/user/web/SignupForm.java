package busy.user.web;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm {

	// FIELDS

	@NotEmpty(message = "{firstname.required}")
	@Length(max = 35, message = "{firstname.maxlength}")
	private String firstName;

	@NotEmpty(message = "{lastname.required}")
	@Length(max = 35, message = "{lastname.maxlength}")
	private String lastName;

	@Email(message = "{email.wrong_format}")
	@NotEmpty(message = "{email.required}")
	@Length(max = 50, message = "{email.maxlength}")
	private String email;

	@NotEmpty(message = "{country.required}")
	private String countryCode;

	@NotEmpty(message = "{city.required}")
	private String cityId;

	@Length(max = 10, message = "{zipcode.maxlength}")
	private String zipCode;

	@Length(min = 8, max = 12, message = "{phone.length}")
	@Pattern(regexp = "[0-9]*", message = "{phone.wrong_format}")
	private String phone;

	@Length(min = 4, max = 50, message = "{password.length}")
	private String password;

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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		
		if(zipCode != "")
			this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		
		if(phone != "")
			this.phone = phone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

}
