package busy.company.web;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.FilterConstructor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import busy.BusyPage;
import busy.notifications.messages.BookingMsg;

/**
 * Company Info Page
 * 
 * @author malkomich
 *
 */
public class CompanyPage extends BusyPage {

    private static final String PARTIAL_PATH = "/company/"; // "/company/{id}"
    private static final String DESCRIPTION = "Company Page";

    public static final int FORM_BOOKING = 1;

    // CSS Selectors
    private static final String BOOKINGS_SECTION_SELECTOR = "#booking-collapse";
    private static final String BRANCH_SECTION_SELECTOR = "#booking-content";
    private static final String BRANCH_SELECTOR = ".branch-item";
    private static final String CALENDAR_MONTH_SELECTOR = ".cal-month-box";
    private static final String DAY_CELL_SELECTOR = ".cal-month-day > span";
    private static final String DAY_CELL_DATE = "data-cal-date";
    private static final String BOOKING_FORM_ID = "booking-form";
    private static final String BOOKING_FORM_WORKER = ".role-select";
    private static final String ERROR_SELECTOR = "span.error";
    private static final String SUBMIT_SELECTOR = "#submit";

    private static final String BRANCH_NAME = "branch-name";
    private static final String AVAILABLE_EVENT_CLASS = "event-info";
    private static final String MESSAGE_SELECTOR = "#infoMessage";

    private int id;

    @Override
    public String relativePath() {

        return PARTIAL_PATH + "/" + String.valueOf(id);
    }

    @Override
    public void isAt() {

        String description = getDriver().findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertThat(description).contains(DESCRIPTION);
    }

    public CompanyPage withId(int id) {

        this.id = id;

        return this;
    }

    public CompanyPage clickOnBookings() {

        click(BOOKINGS_SECTION_SELECTOR);
        waitForJSandJQueryToLoad();
        return this;

    }

    public boolean branchesShown() {

        return findFirst(BRANCH_SECTION_SELECTOR).isDisplayed() && !find(BRANCH_SELECTOR).isEmpty();
    }

    public CompanyPage selectBranch(String branch) {

        FluentWebElement branchItem =
            (branch != null) ? findFirst(BRANCH_SELECTOR, FilterConstructor.with(BRANCH_NAME).equalTo(branch))
                : findFirst(BRANCH_SELECTOR);

        click(branchItem);
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean calendarShown() {

        return findFirst(CALENDAR_MONTH_SELECTOR).isDisplayed();
    }

    public CompanyPage selectDayInCalendar(DateTime day) {

        getDayCell(day).click();
        return this;
    }

    private FluentWebElement getDayCell(DateTime day) {

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
        return findFirst(DAY_CELL_SELECTOR, FilterConstructor.with(DAY_CELL_DATE).equalTo(dtfOut.print(day))).axes()
            .parent();
    }

    public CompanyPage selectTimeSlot(DateTime dateTime) {

        FluentWebElement dayCell = getDayCell(dateTime);
        String startTime = DateTimeFormat.forPattern("HH:mm").print(dateTime);
        FluentWebElement event =
            dayCell.findFirst("a.event-info", FilterConstructor.with("data-original-title").contains(startTime));
        event.click();
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean formIsShown(int formType) {

        String selector = null;

        if (formType == FORM_BOOKING) {
            selector = BOOKING_FORM_ID;
        }

        return getWhenVisible(selector).isDisplayed();
    }

    public CompanyPage selectWorker(String worker) {

        if (!worker.isEmpty()) {
            findFirst(BOOKING_FORM_WORKER).click();
            waitForJSandJQueryToLoad();

            findFirst("option", FilterConstructor.withText().contains(worker)).click();
        }
        return this;
    }

    public boolean bookingSuccess(MessageSource messageSource) {

        FluentWebElement message = findFirst(MESSAGE_SELECTOR);
        String messageExpected = messageSource
            .getMessage(BookingMsg.BOOKING_SUCCESS.getMessageCode(), null, LocaleContextHolder.getLocale()).trim();
        
        return message.isDisplayed() && message.getText().contains(messageExpected);
        
    }

    public boolean timeOptionShown(DateTime dateTime) {

        FluentWebElement dayCell = getDayCell(dateTime);
        String startTime = DateTimeFormat.forPattern("HH:mm").print(dateTime);
        FluentWebElement event =
            dayCell.findFirst(".event", FilterConstructor.with("data-original-title").contains(startTime));
        return event.getAttribute("data-event-class") == AVAILABLE_EVENT_CLASS;
    }

    public boolean bookingFormError() {

        return !find(ERROR_SELECTOR).isEmpty();
    }

    public CompanyPage submit() {

        submit(SUBMIT_SELECTOR);
        waitForJSandJQueryToLoad();

        return this;
    }

}
