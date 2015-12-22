package busy.bdd.accounts;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;

import busy.AbstractFunctionalTest;
import busy.user.web.LoginPage;
import busy.user.web.RegisterPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SignUpSteps extends AbstractFunctionalTest {

	@Page
	private LoginPage loginPage;
	
	@Page
	private RegisterPage registerPage;

	@Given("^the user is on login page$")
	public void user_is_on_login_page() throws Throwable {
		goTo(loginPage).await().untilPage();
		FluentLeniumAssertions.assertThat(loginPage).isAt();
	}
	
	@When("^user clicks on \"Sign Up\"$")
	public void user_clicks_on_signup() throws Throwable {
		loginPage.clickSignUp();
	}
	
	@Then("^the register page is shown$")
	public void register_page_is_shown() throws Throwable {
		FluentLeniumAssertions.assertThat(registerPage).isAt();
	}

	@When("^user introduces the first name ([^\"]*)$")
	public void user_introduces_firstname(String firstname) throws Throwable {
		registerPage.setFirstname(firstname);
	}
	
	@When("^user introduces the last name ([^\"]*)$")
	public void user_introduces_lastname(String lastname) throws Throwable {
		registerPage.setLastname(lastname);
	}
	
	@When("^user introduces the email ([^\"]*)$")
	public void user_introduces_email(String email) throws Throwable {
		registerPage.setEmail(email);
	}
	
	@When("^user introduces the nif ([^\"]*)$")
	public void user_introduces_nif(String nif) throws Throwable {
		registerPage.setNif(nif);
	}
	
	@When("^user selects the country ([^\"]*)$")
	public void user_selects_country(String country) throws Throwable {
		registerPage.selectCountry(country);
	}
	
	@When("^user selects the city ([^\"]*)$")
	public void user_selects_city(String city) throws Throwable {
		registerPage.selectCity(city);
	}
	
	@When("^user introduces the zip code ([^\"]*)$")
	public void user_introduces_zipcode(String zipcode) throws Throwable {
		registerPage.setZipcode(zipcode);
	}
	
	@When("^user introduces the phone number ([^\"]*)$")
	public void user_introduces_phone(String phone) throws Throwable {
		registerPage.setPhoneNumber(phone);
	}
	
	@When("^user introduces the password ([^\"]*)$")
	public void user_introduces_password(String password) throws Throwable {
		registerPage.setPassword(password);
	}

	@When("^user introduces the password confirmation ([^\"]*)$")
	public void user_introduces_password_confirmation(String passConfirm) throws Throwable {
		registerPage.setPasswordConfirmation(passConfirm);
	}

	@When("^user press Sign Up button$")
	public void user_press_Log_In_button() throws Throwable {
		registerPage.submit();
	}

	@Then("^a success message is shown$")
	public void a_success_message_is_shown() throws Throwable {
		assertTrue(registerPage.errorIsShown());
		FluentLeniumAssertions.assertThat(registerPage).isAt();
	}
	
	@Then("^an email to confirm account is sent$")
	public void an_email_to_confirm_is_sent() throws Throwable {
		// Stub method
	}
	
	@When("^user click on \"Validate\" in the email$")
	public void user_click_on_validate() throws Throwable {
		// Stub method
	}
	
	@Then("^a confirm page is shown$")
	public void a_confirm_page_is_shown() throws Throwable {
		// Stub method
	}
	
	@Then("^login page is shown automatically$")
	public void login_page_is_shown() throws Throwable{
		// Stub method
	}

	@Then("^an error message is shown$")
	public void an_error_message_is_shown() throws Throwable {
		assertTrue(registerPage.errorIsShown());
		FluentLeniumAssertions.assertThat(registerPage).isAt();
	}
	
}
