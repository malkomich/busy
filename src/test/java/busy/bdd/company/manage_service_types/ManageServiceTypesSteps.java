package busy.bdd.company.manage_service_types;

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

public class ManageServiceTypesSteps extends AbstractFunctionalTest {

    @Page
    private LoginPage loginPage;

    @Page
    private MainPage mainPage;

    @Page
    private BranchPage branchPage;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/manage_service_types-prepare.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/manage_service_types-rollback.sql";
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

    @Then("^I should see an empty list of services$")
    public void empty_service_type_list() throws Throwable {

        assertTrue(branchPage.serviceTypeListIsEmpty());
    }

    @When("^I click on Add$")
    public void click_on_add() throws Throwable {

        branchPage.clickOnAddServiceType();
    }

    @When("^I click on Modify$")
    public void click_on_modify() throws Throwable {

        branchPage.clickOnModifyServiceType();
    }

    @When("^I click on Delete$")
    public void click_on_delete() throws Throwable {

        branchPage.clickOnDeleteServiceType();
    }

    @Then("^I should see a window with a form to input the data$")
    public void form_shown() throws Throwable {

        assertTrue(branchPage.formIsShown(BranchPage.FORM_SERVICE_TYPE));
    }

    @When("^I enter the name \"([^\"]*)\"$")
    public void enter_name(String name) throws Throwable {

        branchPage.setServiceName(name);
    }

    @When("^I enter the description \"([^\"]*)\"$")
    public void enter_description(String description) throws Throwable {

        branchPage.setServiceDescription(description);
    }

    @When("^I enter the number of maximum bookings per role \"([^\"]*)\"$")
    public void enter_maximum_bookings(String maximumBookings) throws Throwable {

        branchPage.setServiceMaximumBookings(maximumBookings);
    }

    @When("^I enter the duration of the service \"([^\"]*)\"$")
    public void enter_duration(String duration) throws Throwable {

        branchPage.setServiceDuration(duration);
    }

    @When("^I click on Accept$")
    public void submit() throws Throwable {

        branchPage.submitForm(BranchPage.FORM_SERVICE_TYPE);
    }

    @Then("^I should see \"([^\"]*)\" in the list of services with the data \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void data_shown(String name, String description, String maximumBookings, String duration) throws Throwable {

        assertTrue(branchPage.serviceTypeShown(name, description, maximumBookings, duration));
    }

    @Then("^I should see \"([^\"]*)\" in the list of services$")
    public void data_shown(String name) throws Throwable {

        assertTrue(branchPage.serviceTypeShown(name));
    }

    @When("^I add a new type of service \"([^\"]*)\"$")
    public void add_service_type(String name) throws Throwable {

        String scriptPath = "classpath:database/add_service_type.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @When("^I add a service with a booking in the service type \"([^\"]*)\"$")
    public void add_service_with_booking(String name) {

        String scriptPath = "classpath:database/add_service_with_booking.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @Then("^I should see an error in the name field$")
    public void duplicated_name_error() throws Throwable {

        assertTrue(branchPage.duplicateServiceTypeErrorShown());
    }

}
