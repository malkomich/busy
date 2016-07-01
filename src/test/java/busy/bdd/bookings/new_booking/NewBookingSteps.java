package busy.bdd.bookings.new_booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import busy.AbstractFunctionalTest;
import busy.company.web.CompanyPage;
import busy.user.web.LoginPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class NewBookingSteps extends AbstractFunctionalTest {

    private static final String INSERT_SERVICE = "INSERT INTO service(service_type_id, repetition_type) ";
    private static final String INSERT_TIMESLOT = "INSERT INTO time_slot(start_time, service_id) ";
    private static final String INSERT_SCHEDULE = "INSERT INTO schedule(time_slot_id, role_id) ";
    private static final String INSERT_BOOKING = "INSERT INTO booking(person_id, schedule_id) ";

    @Page
    private LoginPage loginPage;

    @Page
    private CompanyPage companyInfoPage;

    private String date;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/new_booking-prepare.sql";
        template.execute(getSQLScript(scriptPath));

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        date = dtfOut.print(new DateTime());

        scriptPath = INSERT_SERVICE + "VALUES((SELECT id FROM service_type WHERE name='Peluquero'), 0);"

            + INSERT_TIMESLOT + "VALUES('" + date
            + "', (SELECT id FROM service WHERE service_type_id=(SELECT id FROM service_type WHERE name='Peluquero')));"

            + INSERT_SCHEDULE + "VALUES((SELECT id FROM time_slot WHERE start_time='" + date + "'),"
            + "(SELECT id FROM role WHERE branch_id=(SELECT id FROM branch WHERE phone='902202122')));";

        template.execute(scriptPath);
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/rollback.sql";
        template.execute(getSQLScript(scriptPath));

        date = null;
    }

    @Given("^I am logged as an user$")
    public void logged_as_user() throws Throwable {

        goTo(loginPage).await().untilPage();
        FluentLeniumAssertions.assertThat(loginPage).isAt();

        String email = "peluqueria@domain.x";
        String password = "pass";

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.submit();
    }

    @Given("^I have a registered company with my account$")
    public void company_registered() throws Throwable {

        // Company added on '@Before' setup method
    }

    @Given("^I am on the info page of a company$")
    public void on_main_page() throws Throwable {

        goTo(companyInfoPage.withId(1)).await().untilPage();
        FluentLeniumAssertions.assertThat(companyInfoPage).isAt();
    }

    @When("^I click on 'Bookings'$")
    public void click_on_bookings() throws Throwable {

        companyInfoPage.clickOnBookings();
    }

    @Then("^I should see a list with the branches of the company$")
    public void branches_shown() throws Throwable {

        assertTrue(companyInfoPage.branchesShown());
    }

    @When("^I select a branch$")
    public void select_branch() throws Throwable {

        // Here we assume there's only a branch stored
        companyInfoPage.selectBranch(null);
    }

    @Then("^I should see a calendar with the available time slots$")
    public void calendar_shown() throws Throwable {

        assertTrue(companyInfoPage.calendarShown());
    }

    @When("^I select the time \"([^\"]*)\" of the day \"([^\"]*)\" in the calendar$")
    public void select_day(String time, String dayTmp) throws Throwable {

        int day = Integer.parseInt(dayTmp);
        DateTime date = new DateTime().withDayOfMonth(day);
        companyInfoPage.selectDayInCalendar(date);
        companyInfoPage.selectTime(time);
    }

    @Then("^I should see a dialog with the available workers$")
    public void booking_dialog_form_shown() throws Throwable {

        assertTrue(companyInfoPage.formIsShown(CompanyPage.FORM_BOOKING));
    }

    @When("^I select the worker \"([^\"]*)\"$")
    public void select_worker(String worker) throws Throwable {

        companyInfoPage.selectWorker(worker);
    }

    @When("^I submit the booking$")
    public void submit_booking() throws Throwable {

        companyInfoPage.submit();
    }

    @Then("^I should see a notification of the booking done$")
    public void booking_success_notification() throws Throwable {

        assertTrue(companyInfoPage.bookingSuccess(messageSource));
    }

    @Then("^I shouldn't see the time \"([^\"]*)\" of the day \"([^\"]*)\" as available in the calendar$")
    public void booking_time_option_not_shown(String time, String day) throws Throwable {

        assertFalse(companyInfoPage.timeOptionShown(time));
    }

    @When("^the time \"([^\"]*)\" is booked by anyone else$")
    public void time_booked() throws Throwable {

        String scriptPath = INSERT_BOOKING + "VALUES((SELECT id FROM person WHERE email='user1@domain.com'), (SELECT "
            + "id FROM schedule WHERE service_id=(SELECT id FROM service WHERE start_time='" + date + "')));";
        template.execute(getSQLScript(scriptPath));
    }

    @Then("^I should see an error message in the dialog$")
    public void booking_dialog_error() throws Throwable {

        assertTrue(companyInfoPage.bookingFormError());
    }

    @When("^the company is inactive$")
    public void company_inactive() throws Throwable {

        String scriptPath = "classpath:database/set_company_inactive.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @Then("^I should see a message with the error$")
    public void error_message_shown() throws Throwable {

        assertTrue(companyInfoPage.errorMessage());
    }

}
