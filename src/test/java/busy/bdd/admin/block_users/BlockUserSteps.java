package busy.bdd.admin.block_users;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;

import busy.AbstractFunctionalTest;
import busy.admin.web.AdminPage;
import busy.user.web.LoginPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BlockUserSteps extends AbstractFunctionalTest {

    @Page
    private LoginPage loginPage;

    @Page
    private AdminPage adminPage;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/block_user-prepare.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/rollback.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @Given("^I am logged as an admin$")
    public void logged_as_admin() throws Throwable {

        goTo(loginPage).await().untilPage();
        FluentLeniumAssertions.assertThat(loginPage).isAt();

        String email = "admin@domain.x";
        String password = "pass";

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.submit();
    }

    @Given("^I am on the main admin page$")
    public void on_admin_page() throws Throwable {

        goTo(adminPage).await().untilPage();
        FluentLeniumAssertions.assertThat(adminPage).isAt();
    }

    @When("^I click on users section$")
    public void click_on_users_section() throws Throwable {

        adminPage.clickOnUsersSection();
    }

    @Then("^I should see a detailed list of all the users in the system$")
    public void user_list_shown() throws Throwable {

        assertTrue(adminPage.userListShown());
    }

    @Then("^I should see the user \"([^\"]*)\" as active$")
    public void user_active(String name) throws Throwable {

        assertTrue(adminPage.userActiveStatus(name, true));
    }

    @When("^I click on Block in the row of user \"([^\"]*)\"$")
    public void block_user(String name) throws Throwable {

        adminPage.toogleActiveStatus(AdminPage.USER, name);
    }

    @Then("^I should see a confirmation message$")
    public void confirmation_shown() throws Throwable {

        assertTrue(adminPage.blockUserConfirmationShown());
    }

    @Then("^I should see the user \"([^\"]*)\" as blocked$")
    public void user_blocked(String name) throws Throwable {

        assertTrue(adminPage.userActiveStatus(name, false));
    }

}
