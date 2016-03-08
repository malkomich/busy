package busy.bdd.accounts.sign_up;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;

import busy.AbstractFunctionalTest;
import busy.user.web.ConfirmPage;
import busy.user.web.LoginPage;
import busy.user.web.SignupPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Feature: A new user will be able to sign up to get an account in the system.
 * 
 * @author malkomich
 *
 */
public class SignUpSteps extends AbstractFunctionalTest {

	@Page
	private LoginPage loginPage;

	@Page
	private SignupPage signupPage;

	@Page
	private ConfirmPage confirmPage;

	@PostConstruct
	public void runOnce() {

		String scriptPath = "classpath:database/signup-prepare.sql";
		template.execute(getSQLScript(scriptPath));
	}

	@PreDestroy
	public void rollback() {

		String scriptPath = "classpath:database/signup-rollback.sql";
		template.execute(getSQLScript(scriptPath));
	}

	@Given("^the user is on login page$")
	public void user_is_on_login_page() throws Throwable {
		goTo(loginPage).await().untilPage();
		FluentLeniumAssertions.assertThat(loginPage).isAt();
	}
	
	@When("^the user clicks on \"Sign Up\"$")
	public void user_clicks_on_signup() throws Throwable {
		loginPage.clickSignUp();
	}

	@Then("^the register page is shown$")
	public void register_page_is_shown() throws Throwable {
		FluentLeniumAssertions.assertThat(signupPage).isAt();
	}

	@When("^the user introduces the first name \"([^\"]*)\"$")
	public void user_introduces_firstname(String firstname) throws Throwable {
		signupPage.setFirstname(firstname);
	}

	@When("^the user introduces the last name \"([^\"]*)\"$")
	public void user_introduces_lastname(String lastname) throws Throwable {
		signupPage.setLastname(lastname);
	}

	@When("^the user introduces the email \"([^\"]*)\"$")
	public void user_introduces_email(String email) throws Throwable {
		signupPage.setEmail(email);
	}

	@When("^the user selects the country \"([^\"]*)\"$")
	public void user_selects_country(String country) throws Throwable {
		signupPage.selectCountry(country);
	}

	@When("^the user selects the city \"([^\"]*)\"$")
	public void user_selects_city(String city) throws Throwable {
		signupPage.selectCity(city);
	}

	@When("^the user introduces the zip code \"([^\"]*)\"$")
	public void user_introduces_zipcode(String zipcode) throws Throwable {
		signupPage.setZipcode(zipcode);
	}

	@When("^the user introduces the phone number \"([^\"]*)\"$")
	public void user_introduces_phone(String phone) throws Throwable {
		signupPage.setPhoneNumber(phone);
	}

	@When("^the user introduces the password \"([^\"]*)\"$")
	public void user_introduces_password(String password) throws Throwable {
		signupPage.setPassword(password);
	}

	@When("^the user introduces the password confirmation \"([^\"]*)\"$")
	public void user_introduces_password_confirmation(String passConfirm) throws Throwable {
		signupPage.setPasswordConfirmation(passConfirm);
	}

	@When("^the user press Sign Up button$")
	public void user_press_Log_In_button() throws Throwable {
		signupPage.submit();
	}

	@Then("^an email to confirm account is sent$")
	public void an_email_to_confirm_is_sent() throws Throwable {
		assertTrue(signupPage.emailIsSent());
	}
	
	@Then("^a success message is shown$")
	public void a_success_message_is_shown() throws Throwable {
		loginPage.await().untilPage();
		assertTrue(loginPage.signupSuccessMessageIsShown());
	}

	@When("^the user click on \"Validate\" in the email$")
	public void user_click_on_validate() throws Throwable {
		goTo(confirmPage).await().atMost(1, TimeUnit.SECONDS).untilPage();
	}

	@Then("^a confirm message is shown$")
	public void a_confirm_page_is_shown() throws Throwable {
		assertTrue(loginPage.validationMessageIsShown());
	}

	@Then("^the Login page is shown automatically$")
	public void login_page_is_shown() throws Throwable {
		FluentLeniumAssertions.assertThat(loginPage).isAt();
	}

	@Then("^an error message in the SignUp page is shown$")
	public void an_error_message_is_shown() throws Throwable {
		assertTrue(signupPage.errorIsShown());
		FluentLeniumAssertions.assertThat(signupPage).isAt();
	}

}
