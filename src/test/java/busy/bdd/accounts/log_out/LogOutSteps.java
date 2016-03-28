package busy.bdd.accounts.log_out;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import busy.AbstractFunctionalTest;
import busy.user.web.LoginPage;
import busy.user.web.MainPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Feature: The user or admin will be able to log in the system
 * 
 * @author malkomich
 *
 */
public class LogOutSteps extends AbstractFunctionalTest {

    final Logger log = LoggerFactory.getLogger(LogOutSteps.class);

    @Page
    private MainPage mainPage;

    @Page
    private LoginPage loginPage;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/login-prepare.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/login-rollback.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @Given("^the user has logged in his account$")
    public void user_has_logged_in() throws Throwable {

        goTo(loginPage).await().untilPage();
        FluentLeniumAssertions.assertThat(loginPage).isAt();

        String email = "user@domain.com";
        String password = "pass";

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.submit();
    }

    @Given("^the user is on main page$")
    public void user_is_on_main_page() throws Throwable {

        goTo(mainPage).await().untilPage();
        FluentLeniumAssertions.assertThat(mainPage).isAt();
    }

    @When("^the user clicks on \"Log out\" in the top bar$")
    public void user_clicks_log_out() throws Throwable {

        mainPage.clickOnLogOut();
    }

    @Then("^the user should see the login page$")
    public void loginPageIsShown() throws Throwable {

        FluentLeniumAssertions.assertThat(loginPage).isAt();
    }

}
