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
    private static final String BOOKINGS_SECTION_SELECTOR = "#section-bookings";
    private static final String BRANCH_LIST_SELECTOR = "#branch-list";
    private static final String BRANCH_SELECTOR = "#branch-item";
    private static final String CALENDAR_MONTH_SELECTOR = ".cal-month-box";
    private static final String DAY_CELL_SELECTOR = ".cal-month-day span";
    private static final String DAY_CELL_DATE = "data-cal-date";
    private static final String BOOKING_FORM_SELECTOR = ".booking-form";
    private static final String BOOKING_FORM_TIME = "#booking-time";
    private static final String BOOKING_FORM_WORKER = "#booking-worker";
    private static final String ITEM_NOTIFICATION_MESSAGE_SELECTOR = "div.item-notification-message";
    private static final String ERROR_SELECTOR = "span.error";
    private static final String MESSAGE_SELECTOR = "#infoMessage";
    private static final String SUBMIT_SELECTOR = "#submit";

    private static final String BRANCH_NAME = "branch-name";

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
        return this;

    }

    public boolean branchesShown() {

        return !findFirst(BRANCH_LIST_SELECTOR).find(BRANCH_SELECTOR).isEmpty();
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

    public boolean formIsShown(int formType) {

        String selector = null;

        if (formType == FORM_BOOKING) {
            selector = BOOKING_FORM_SELECTOR;

        }

        return findFirst(selector).isDisplayed();
    }

    public CompanyPage selectTime(String time) {

        if (!time.isEmpty()) {
            FluentWebElement select = findFirst(BOOKING_FORM_TIME);

            FluentWebElement option = select.findFirst("option", FilterConstructor.containingText(time));
            if (!option.isDisplayed()) {
                select.click();
            }
            option.click();
        }
        return this;
    }

    public CompanyPage selectWorker(String worker) {

        if (!worker.isEmpty()) {
            FluentWebElement select = findFirst(BOOKING_FORM_WORKER);

            FluentWebElement option = select.findFirst("option", FilterConstructor.containingText(worker));
            if (!option.isDisplayed()) {
                select.click();
            }
            option.click();
        }
        return this;
    }

    public boolean bookingSuccess(MessageSource messageSource) {

        String messageExpected = messageSource
            .getMessage(BookingMsg.BOOKING_SUCCESS.getMessageCode(), null, LocaleContextHolder.getLocale()).trim();
        for (FluentWebElement notification : find(ITEM_NOTIFICATION_MESSAGE_SELECTOR)) {
            String messageActual = notification.getText().trim();
            if (messageActual.equals(messageExpected)) {
                return true;
            }
        }
        return false;
    }

    public boolean timeOptionShown(String time) {

        if (!time.isEmpty()) {
            FluentWebElement select = findFirst(BOOKING_FORM_TIME);

            FluentWebElement option = select.findFirst("option", FilterConstructor.containingText(time));
            if (!option.isDisplayed()) {
                select.click();
            }
            if (option.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    public boolean bookingFormError() {

        return !find(ERROR_SELECTOR).isEmpty();
    }

    public boolean errorMessage() {

        return findFirst(MESSAGE_SELECTOR).isDisplayed();
    }

    public CompanyPage submit() {

        submit(SUBMIT_SELECTOR);

        return this;
    }

}
