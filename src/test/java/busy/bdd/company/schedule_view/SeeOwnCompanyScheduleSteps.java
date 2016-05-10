package busy.bdd.company.schedule_view;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import busy.AbstractFunctionalTest;
import busy.company.web.BranchPage;
import busy.user.web.LoginPage;
import busy.user.web.MainPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SeeOwnCompanyScheduleSteps extends AbstractFunctionalTest {

    final Logger log = LoggerFactory.getLogger(SeeOwnCompanyScheduleSteps.class);

    private static final String INSERT_SERVICE = "INSERT INTO service(start_time, service_type_id, role_id) ";
    private static final String INSERT_BOOKING = "INSERT INTO booking(person_id, service_id) ";

    @Page
    private LoginPage loginPage;

    @Page
    private MainPage mainPage;

    @Page
    private BranchPage branchPage;

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

    @When("^I add some services with bookings for the current day$")
    public void add_services_and_bookings() throws Throwable {

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String query = INSERT_SERVICE + "VALUES('" + dtfOut.print(new DateTime()) + "',(SELECT id FROM service_type "
                + "WHERE name='Engineer'),(SELECT id FROM role WHERE branch_id=(SELECT id FROM branch "
                + "WHERE phone='902202122')));"
                + INSERT_SERVICE + "VALUES('" + dtfOut.print(new DateTime().plusHours(2)) + "',(SELECT id FROM "
                + "service_type WHERE name='Engineer'),(SELECT id FROM role WHERE branch_id=(SELECT id FROM branch "
                + "WHERE phone='902202122')));"
                + INSERT_BOOKING + "VALUES((SELECT id FROM person WHERE email='user1@domain.com'), (SELECT id FROM "
                + "service WHERE start_time='" + dtfOut.print(new DateTime()) + "'));"
                + INSERT_BOOKING + "VALUES((SELECT id FROM person WHERE email='user2@domain.com'), "
                + "(SELECT id FROM service WHERE start_time='" + dtfOut.print(new DateTime().plusHours(2)) + "'));";
        template.execute(query);
    }

    @When("^I select my company name '(.+)' in the dropdown$")
    public void select_company_section(String companyName) throws Throwable {

        mainPage.selectCompanySection(companyName);
    }

    @Then("^I should see a calendar with the bookings already made$")
    public void see_calendar() throws Throwable {

        FluentLeniumAssertions.assertThat(branchPage).isAt();
        assertTrue(branchPage.calendarShown());
    }

    @When("^I select the current day$")
    public void select_a_day() throws Throwable {

        int today = new DateTime().getDayOfMonth();
        branchPage.selectDayInCalendar(today);
    }

    @Then("^I should see the bookings of this day in a more detailed way$")
    public void see_detailed_calendar() throws Throwable {

        assertTrue(branchPage.dayEventsDetailedShown());
    }

}
