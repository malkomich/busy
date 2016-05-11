package busy.bdd.company.create_services;

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

public class CreateServicesSteps extends AbstractFunctionalTest {

    @Page
    private LoginPage loginPage;

    @Page
    private MainPage mainPage;

    @Page
    private BranchPage branchPage;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/create_services-prepare.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/create_services-rollback.sql";
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

    @Given("^I select my company name \"([^\"]*)\" in the dropdown$")
    public void select_company_section(String companyName) throws Throwable {

        mainPage.selectCompanySection(companyName);
    }

    @When("^I add at least one service type$")
    public void add_service_types() throws Throwable {

        String scriptPath = "classpath:database/add_service_type.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @When("^I double click the cell of day \"([^\"]*)\" in the schedule$")
    public void double_click_day_cell(String dayTmp) throws Throwable {

        int day = Integer.parseInt(dayTmp);
        branchPage.dblClickDayCell(day);
    }

    @Then("^I should see a message to create at least one service type$")
    public void error_service_type_shown() throws Throwable {

        branchPage.messageShown(BranchPage.MESSAGE_SERVICE_TYPE);
    }

    @Then("^I should see a dialog to enter the data of the new service$")
    public void form_dialog_shown() throws Throwable {

        branchPage.formIsShown(BranchPage.FORM_SERVICE);
    }

    @When("^I introduce the start time \"([^\"]*)\"$")
    public void introduce_start_time(String startTime) throws Throwable {

        branchPage.setServiceStartTime(startTime);
    }

    @When("^I select the service type \"([^\"]*)\"$")
    public void select_service_type(String serviceType) throws Throwable {

        branchPage.selectServiceType(serviceType);
    }

    @When("^I select the role/roles \"([^\"]*)\"$")
    public void select_roles(String roles) throws Throwable {

        branchPage.selectServiceRoles(roles);
    }

    @When("^I select the repetition \"([^\"]*)\"$")
    public void select_repetition(String repetition) throws Throwable {

        branchPage.selectRepetition(repetition, messageSource);
    }

    @When("^I click on 'Save'$")
    public void click_save() throws Throwable {

        branchPage.submitForm(BranchPage.FORM_SERVICE);
    }

    @Then("^I should see an error message in the form$")
    public void form_error_shown() throws Throwable {

        branchPage.errorShown(BranchPage.FORM_SERVICE);
    }

    @Then("^I should see the service created on day \"([^\"]*)\"$")
    public void service_created(String dayTmp) throws Throwable {

        int day = Integer.parseInt(dayTmp);
        branchPage.serviceCreated(day);
    }

    @Then("^I should see the service created on the days after the day \"([^\"]*)\" according to the repeat "
            + "type \"([^\"]*)\"$")
    public void service_repeated(String dayTmp, String repetition) throws Throwable {

        int day = Integer.parseInt(dayTmp);
        branchPage.serviceRepeated(day, repetition);
    }

}
