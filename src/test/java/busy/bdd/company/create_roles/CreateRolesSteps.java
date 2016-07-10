package busy.bdd.company.create_roles;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;

import busy.AbstractFunctionalTest;
import busy.company.web.BranchPage;
import busy.user.web.LoginPage;
import busy.user.web.MainPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateRolesSteps extends AbstractFunctionalTest {

    @Page
    private LoginPage loginPage;

    @Page
    private MainPage mainPage;

    @Page
    private BranchPage branchPage;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/create_roles-prepare.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/rollback.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @Given("^I am logged as an user$")
    public void logged_as_user() throws Throwable {

        goTo(loginPage).await().untilPage();
        FluentLeniumAssertions.assertThat(loginPage).isAt();

        String email = "busy.validation@gmail.com";
        String password = "pass";

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.submit();
    }

    @Given("^I have a registered company with my account$")
    public void company_registered() throws Throwable {

        // Company added on '@Before' setup method
    }

    @Given("^I am on the main page$")
    public void on_main_page() throws Throwable {

        goTo(mainPage).await().untilPage();
        FluentLeniumAssertions.assertThat(mainPage).isAt();
    }

    @When("^I select my company name \"([^\"]*)\" in the dropdown$")
    public void select_company_section(String companyName) throws Throwable {

        mainPage.selectCompanySection(companyName);
    }

    @Then("^I should see an empty list of employees$")
    public void empty_service_type_list() throws Throwable {

        assertTrue(branchPage.roleListEmpty());
    }

    @When("^I click on Add$")
    public void click_on_add() throws Throwable {

        branchPage.clickOnAdd(BranchPage.ROLE);
    }

    @Then("^I should see a window with a form to input the data$")
    public void form_shown() throws Throwable {

        assertTrue(branchPage.formIsShown(BranchPage.ROLE));
    }

    @When("^I enter the name \"([^\"]*)\"$")
    public void enter_name(String name) throws Throwable {

        branchPage.setRoleName(name);
    }

    @When("^I enter the email \"([^\"]*)\"$")
    public void enter_email(String email) throws Throwable {

        branchPage.setRoleEmail(email);
    }

    @When("^I enter the phone number \"([^\"]*)\"$")
    public void enter_phone(String phoneNumber) throws Throwable {

        branchPage.setRolePhoneNumber(phoneNumber);
    }

    @When("^I click on Accept$")
    public void submit() throws Throwable {

        branchPage.submitForm(BranchPage.ROLE);
    }
    
    @Then("^I should see a confirmation message$")
    public void confirmationMessage() throws Throwable {

        assertTrue(branchPage.messageShown(BranchPage.ROLE));
    }

    @Then("^I should see \"([^\"]*)\" in the list of employees$")
    public void data_shown(String name) throws Throwable {

        assertTrue(branchPage.roleShown(name));
    }

    @Then("^I should see an error message$")
    public void error() throws Throwable {

        assertTrue(branchPage.errorShown(BranchPage.ROLE));
    }

}
