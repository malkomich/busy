package busy.bdd.accounts;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;

import busy.AbstractFunctionalTest;
import busy.user.web.ConfirmPage;
import busy.user.web.LoginPage;
import busy.user.web.RegisterPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SignUpSteps extends AbstractFunctionalTest {

	@Page
	private LoginPage loginPage;
	
	@Page
	private RegisterPage registerPage;
	
	@Page
	private ConfirmPage confirmPage;
	
	@Before
	public void runOnce() {

		String scriptPath = "classpath:database/test/insert-person.sql";
		template.execute(getSQLScript(scriptPath));
		
		scriptPath = "classpath:database/test/insert-registry.sql";
		template.execute(getSQLScript(scriptPath));
		
	}
	
	@After
	public void rollback() {

		String scriptPath = "classpath:database/test/drop-person.sql";
		template.execute(getSQLScript(scriptPath));
	}

	@When("^the user clicks on \"Sign Up\"$")
	public void user_clicks_on_signup() throws Throwable {
		loginPage.clickSignUp();
	}
	
	@Then("^the register page is shown$")
	public void register_page_is_shown() throws Throwable {
		FluentLeniumAssertions.assertThat(registerPage).isAt();
	}

	@When("^the user introduces the first name ([^\"]*)$")
	public void user_introduces_firstname(String firstname) throws Throwable {
		registerPage.setFirstname(firstname);
	}
	
	@When("^the user introduces the last name ([^\"]*)$")
	public void user_introduces_lastname(String lastname) throws Throwable {
		registerPage.setLastname(lastname);
	}
	
	@When("^the user introduces the email ([^\"]*)$")
	public void user_introduces_email(String email) throws Throwable {
		registerPage.setEmail(email);
	}
	
	@When("^the user introduces the nif ([^\"]*)$")
	public void user_introduces_nif(String nif) throws Throwable {
		registerPage.setNif(nif);
	}
	
	@When("^the user selects the country ([^\"]*)$")
	public void user_selects_country(String country) throws Throwable {
		registerPage.selectCountry(country);
	}
	
	@When("^the user selects the city ([^\"]*)$")
	public void user_selects_city(String city) throws Throwable {
		registerPage.selectCity(city);
	}
	
	@When("^the user introduces the zip code ([^\"]*)$")
	public void user_introduces_zipcode(String zipcode) throws Throwable {
		registerPage.setZipcode(zipcode);
	}
	
	@When("^the user introduces the phone number ([^\"]*)$")
	public void user_introduces_phone(String phone) throws Throwable {
		registerPage.setPhoneNumber(phone);
	}
	
	@When("^the user introduces the password ([^\"]*)$")
	public void user_introduces_password(String password) throws Throwable {
		registerPage.setPassword(password);
	}

	@When("^the user introduces the password confirmation ([^\"]*)$")
	public void user_introduces_password_confirmation(String passConfirm) throws Throwable {
		registerPage.setPasswordConfirmation(passConfirm);
	}

	@When("^the user press Sign Up button$")
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
		assertTrue(registerPage.emailIsSent());
	}
	
	@When("^the user click on \"Validate\" in the email$")
	public void user_click_on_validate() throws Throwable {
		goTo(confirmPage).await().untilPage();
		
	}
	
	@Then("^a confirm page is shown$")
	public void a_confirm_page_is_shown() throws Throwable {
		FluentLeniumAssertions.assertThat(confirmPage).isAt();
		confirmPage.await().atMost(5, TimeUnit.SECONDS);
	}
	
	@Then("^the login page is shown automatically$")
	public void login_page_is_shown() throws Throwable{
		FluentLeniumAssertions.assertThat(loginPage).isAt();
	}

	@Then("^an error message in the Register page is shown$")
	public void an_error_message_is_shown() throws Throwable {
		assertTrue(registerPage.errorIsShown());
		FluentLeniumAssertions.assertThat(registerPage).isAt();
	}
	
}
