package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;

import busy.BusyPage;

public class RegisterPage extends BusyPage {

	private static final String PATH = "register";
	private static final String DESCRIPTION = "Register Page";

	private static final String FIRSTNAME_SELECTOR = "#firstname";
	private static final String LASTNAME_SELECTOR = "#lastname";
	private static final String EMAIL_SELECTOR = "#email";
	private static final String NIF_SELECTOR = "#nif";
	private static final String COUNTRY_SELECTOR = "#country";
	private static final String CITY_SELECTOR = "#city";
	private static final String ZIPCODE_SELECTOR = "#zipcode";
	private static final String PHONE_SELECTOR = "#phone";
	private static final String PASSWORD_SELECTOR = "#password";
	private static final String PASSCONFIRM_SELECTOR = "#confirmedPassword";
	private static final String SUBMIT_SELECTOR = "#submit";
	
	@Override
	public String relativePath() {

		return PATH;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fluentlenium.core.FluentPage#isAt()
	 */
	@Override
	public void isAt() {

		String description = getDriver().findElement(By.xpath("//meta[@name='description']"))
				.getAttribute("content");
		assertThat(description).contains(DESCRIPTION);
	}
	
	public RegisterPage setFirstname(String firstname) {

		fill(FIRSTNAME_SELECTOR).with(firstname);
		return this;
	}
	
	public RegisterPage setLastname(String lastname) {

		fill(LASTNAME_SELECTOR).with(lastname);
		return this;
	}
	
	public RegisterPage setEmail(String email) {

		fill(EMAIL_SELECTOR).with(email);
		return this;
	}
	
	public RegisterPage setNif(String nif) {

		fill(NIF_SELECTOR).with(nif);
		return this;
	}
	
	public RegisterPage selectCountry(String country) {

		return this;
	}
	
	public RegisterPage selectCity(String city) {

		return this;
	}
	
	public RegisterPage setZipcode(String zipcode) {

		fill(ZIPCODE_SELECTOR).with(zipcode);
		return this;
	}
	
	public RegisterPage setPhoneNumber(String phone) {

		fill(PHONE_SELECTOR).with(phone);
		return this;
	}

	public RegisterPage setPassword(String password) {

		fill(PASSWORD_SELECTOR).with(password);
		return this;
	}
	
	public RegisterPage setPasswordConfirmation(String passConfirm) {

		fill(PASSCONFIRM_SELECTOR).with(passConfirm);
		return this;
	}

	public RegisterPage submit() {

		submit(SUBMIT_SELECTOR);
		return this;
	}

	public boolean errorIsShown() {
		// TODO Auto-generated method stub
		return false;
	}

}
