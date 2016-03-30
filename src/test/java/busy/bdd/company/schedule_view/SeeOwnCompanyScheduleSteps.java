package busy.bdd.company.schedule_view;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import busy.AbstractFunctionalTest;
import busy.company.web.CompanyPage;
import busy.user.web.LoginPage;
import busy.user.web.MainPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SeeOwnCompanyScheduleSteps extends AbstractFunctionalTest {

    final Logger log = LoggerFactory.getLogger(SeeOwnCompanyScheduleSteps.class);

    @Page
    private LoginPage loginPage;

    @Page
    private MainPage mainPage;

    @Page
    private CompanyPage companyPage;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/see_my_company_schedule-prepare.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/see_my_company_schedule-rollback.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @Given("^I am logged as an user$")
    public void logged_as_user() throws Throwable {

        goTo(loginPage).await().untilPage();
        FluentLeniumAssertions.assertThat(loginPage).isAt();

        String email = "user@domain.com";
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

    @When("^I select my company name '(.+)' in the dropdown$")
    public void select_company_section(String companyName) throws Throwable {

        mainPage.selectCompanySection(companyName);
    }

    @Then("^I should see a calendar with the bookings already made$")
    public void see_calendar() throws Throwable {

        FluentLeniumAssertions.assertThat(companyPage).isAt();
        assertTrue(companyPage.calendarShown());
    }

    @When("^I select a day$")
    public void select_a_day() throws Throwable {

        companyPage.selectDayInCalendar();
    }

    @Then("^I should see the bookings of this day in a more detailed way$")
    public void see_detailed_calendar() throws Throwable {

        assertTrue(companyPage.calendarDayShown());
    }

}
