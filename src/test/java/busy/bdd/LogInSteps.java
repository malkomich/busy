package busy.bdd;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;

import busy.AbstractFunctionalTest;
import busy.user.web.HomePage;
import busy.user.web.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Feature: The user or admin will be able to log in the system
 * 
 * @author malkomich
 *
 */
@Sql("/sql/insert_test.sql")
public class LogInSteps extends AbstractFunctionalTest {

	final Logger log = LoggerFactory.getLogger(LogInSteps.class);

	@Page
	private HomePage homePage;

	@Page
	private LoginPage loginPage;

	@Given("^user is on login page$")
	public void user_is_on_login_page() throws Throwable {
		goTo(loginPage).await().untilPage();
		FluentLeniumAssertions.assertThat(loginPage).isAt();
	}

	@When("^user introduces email \"([^\"]*)\"$")
	public void user_introduces_email(String email) throws Throwable {
		loginPage.setEmail(email);
	}

	@When("^user introduces password \"([^\"]*)\"$")
	public void user_introduces_password(String password) throws Throwable {
		loginPage.setPassword(password);
	}

	@When("^user press Log In button$")
	public void user_press_Log_In_button() throws Throwable {
		loginPage.submit();
	}

	@Then("^the Main page is shown$")
	public void mainPageIsShown() throws Throwable {
		FluentLeniumAssertions.assertThat(homePage).isAt();
	}

	@Then("^an error message is shown$")
	public void an_error_message_is_shown() throws Throwable {
		assertTrue(loginPage.errorIsShown());
	}

}
