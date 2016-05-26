package busy.company.web;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.FilterConstructor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import busy.BusyPage;
import busy.schedule.Service.Repetition;

/**
 * Branch Page
 * 
 * @author malkomich
 *
 */
public class BranchPage extends BusyPage {

    // Assures the maximum day for all possible months
    private static final int MAX_DAY_OF_MONTH = 28;

    /**
     * Method flow modifiers
     */
    public static final int MESSAGE_SERVICE_TYPE = 0;
    public static final int FORM_SERVICE_TYPE = 0;
    public static final int FORM_SERVICE = 1;

    private static final String PARTIAL_PATH = "/company/"; // "/company/{id}/branch/{id}"
    private static final String DESCRIPTION = "Branch Page";

    /*
     * CSS Selectors
     */
    private static final String CALENDAR_MONTH_SELECTOR = ".cal-month-box";
    private static final String DAY_CELL_SELECTOR = ".cal-month-day";
    private static final String DAY_EVENTS_DETAILED_SELECTOR = ".cal-event-list";
    private static final String DAY_EVENTS_SELECTOR = ".events-list";
    private static final String EVENT_SELECTOR = ".events";

    private static final String SERVICE_TYPE_LIST_SELECTOR = "#service-type-list";
    private static final String SERVICE_TYPE_ITEM_SELECTOR = ".service-type-item";
    private static final String[] SERVICE_TYPE_PARAMS_SELECTOR = {".name", ".description", ".bookings", ".duration"};
    private static final String SERVICE_TYPE_ADD_SELECTOR = ".service-type_add";
    private static final String SERVICE_TYPE_MODIFY_SELECTOR = ".service-type_modify";
    private static final String SERVICE_TYPE_DELETE_SELECTOR = ".service-type_delete";

    private static final String SERVICE_TYPE_FORM_SELECTOR = ".service-type-form";
    private static final String SERVICE_TYPE_FORM_NAME_SELECTOR = "#name";
    private static final String SERVICE_TYPE_FORM_DESCRIPTION_SELECTOR = "#description";
    private static final String SERVICE_TYPE_FORM_BOOKINGS_SELECTOR = "#maxBookingsPerRole";
    private static final String SERVICE_TYPE_FORM_DURATION_SELECTOR = "#duration";
    private static final String SERVICE_TYPE_FORM_SUBMIT_SELECTOR = "#submit";
    private static final String SERVICE_TYPE_FORM_ERROR_SELECTOR = "span.error";

    private static final String SERVICE_FORM_SELECTOR = ".service-form";
    private static final String SERVICE_FORM_START_TIME = "#service-start-time";
    private static final String SERVICE_FORM_ROLES = ".role-list";
    private static final String SERVICE_FORM_STYPE = "#service-type";
    private static final String SERVICE_FORM_REPETITION_TYPE = "#service-repetition-type";
    private static final String SERVICE_FORM_REPETITION_DATE = "#service-repetition-date";

    private static final String MESSAGE_SELECTOR = "#infoMessage";
    private static final String ERROR_SELECTOR = "span.error";

    @Override
    public String relativePath() {

        return PARTIAL_PATH;
    }

    @Override
    public void isAt() {

        String description = getDriver().findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertThat(description).contains(DESCRIPTION);
    }

    public boolean calendarShown() {

        return findFirst(CALENDAR_MONTH_SELECTOR).isDisplayed();
    }

    public BranchPage selectDayInCalendar(int day) {

        findFirst(DAY_CELL_SELECTOR, FilterConstructor.withText(String.valueOf(day))).click();
        return this;
    }

    public BranchPage dblClickDayCell(int day) {

        findFirst(DAY_CELL_SELECTOR, FilterConstructor.withText(String.valueOf(day))).doubleClick();
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean dayEventsDetailedShown() {

        return findFirst(DAY_EVENTS_DETAILED_SELECTOR).isDisplayed();
    }

    public boolean serviceTypeListIsEmpty() {

        FluentWebElement serviceTypeList = findFirst(SERVICE_TYPE_LIST_SELECTOR);
        return serviceTypeList.find(SERVICE_TYPE_ITEM_SELECTOR).isEmpty();
    }

    public boolean serviceTypeShown(String... serviceProperties) {

        FluentWebElement serviceTypeList = findFirst(SERVICE_TYPE_LIST_SELECTOR);
        for (FluentWebElement item : serviceTypeList.find(SERVICE_TYPE_ITEM_SELECTOR)) {

            int matches = 0;
            for (int i = 0; i < serviceProperties.length; i++) {
                if (SERVICE_TYPE_PARAMS_SELECTOR[i].contains("description")) {
                    if (item.findFirst(SERVICE_TYPE_PARAMS_SELECTOR[0]).getAttribute("title")
                        .contains(serviceProperties[i])) {
                        matches++;
                    }
                } else if (item.findFirst(SERVICE_TYPE_PARAMS_SELECTOR[i]).getText().contains(serviceProperties[i])) {
                    matches++;
                }
            }

            if (matches == serviceProperties.length) {
                return true;
            }
        }
        return false;
    }

    public BranchPage clickOnAddServiceType() {

        findFirst(SERVICE_TYPE_ADD_SELECTOR).click();
        waitForJSandJQueryToLoad();
        return this;
    }

    public BranchPage clickOnModifyServiceType() {

        findFirst(SERVICE_TYPE_MODIFY_SELECTOR).click();
        waitForJSandJQueryToLoad();
        return this;
    }

    public BranchPage clickOnDeleteServiceType() {

        findFirst(SERVICE_TYPE_DELETE_SELECTOR).click();
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean formIsShown(int formType) {

        String selector = null;
        if (formType == FORM_SERVICE_TYPE) {
            selector = SERVICE_TYPE_FORM_SELECTOR;
        } else if (formType == FORM_SERVICE) {
            selector = SERVICE_FORM_SELECTOR;
        }
        return findFirst(selector).isDisplayed();
    }

    public BranchPage setName(String name) {

        fill(SERVICE_TYPE_FORM_NAME_SELECTOR).with(name);
        return this;
    }

    public BranchPage setServiceName(String name) {

        fill(SERVICE_TYPE_FORM_NAME_SELECTOR).with(name);
        return this;
    }

    public BranchPage setServiceDescription(String description) {

        fill(SERVICE_TYPE_FORM_DESCRIPTION_SELECTOR).with(description);
        return this;
    }

    public BranchPage setServiceMaximumBookings(String maximumBookings) {

        fill(SERVICE_TYPE_FORM_BOOKINGS_SELECTOR).with(maximumBookings);
        return this;
    }

    public BranchPage setServiceDuration(String duration) {

        fill(SERVICE_TYPE_FORM_DURATION_SELECTOR).with(duration);
        return this;
    }

    public BranchPage submitForm(int formType) {

        submit(SERVICE_TYPE_FORM_SUBMIT_SELECTOR);
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean duplicateServiceTypeErrorShown() {

        return !find(SERVICE_TYPE_FORM_ERROR_SELECTOR).isEmpty();
    }

    public boolean messageShown(int messageType) {

        return findFirst(MESSAGE_SELECTOR).isDisplayed();
    }

    public BranchPage setServiceStartTime(String startTime) {

        fill(SERVICE_FORM_START_TIME).with(startTime);
        return this;
    }

    public BranchPage selectServiceType(String serviceType) {

        if (!serviceType.isEmpty()) {
            FluentWebElement select = findFirst(SERVICE_FORM_STYPE);
            select.find("option", FilterConstructor.containingText(serviceType)).click();
        }

        return this;
    }

    public BranchPage selectServiceRoles(String rolesTmp) {

        if (!rolesTmp.isEmpty()) {

            FluentWebElement list = findFirst(SERVICE_FORM_ROLES);

            String[] roles = rolesTmp.split(",");
            for (String role : roles) {
                FluentWebElement item = list.findFirst("li", FilterConstructor.containingText(role));
                item.find("input[type=checkbox]").click();
            }
        }

        return this;
    }

    public BranchPage selectRepetition(String repetitionTmp, MessageSource messageSource) {

        Repetition repetition = parseRepetition(repetitionTmp);

        if (repetition != null) {
            String repetitionLabel =
                messageSource.getMessage(repetition.getMsgCode(), null, LocaleContextHolder.getLocale()).trim();
            Select select = new Select(getDriver().findElement(By.cssSelector(SERVICE_FORM_REPETITION_TYPE)));
            select.selectByVisibleText(repetitionLabel);
        }

        return this;
    }

    public BranchPage setRepetitionDate(int days, MessageSource messageSource) {

        DateTime date = new DateTime().plusDays(days);
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy");
        fill(SERVICE_FORM_REPETITION_DATE).with(dtfOut.print(date));
        return this;
    }

    public boolean errorShown(int formType) {

        return !find(ERROR_SELECTOR).isEmpty();
    }

    public boolean serviceCreated(int day) {

        FluentWebElement dayCell = findFirst(DAY_CELL_SELECTOR, FilterConstructor.withText(String.valueOf(day)));

        FluentWebElement eventList;
        try {
            eventList = dayCell.findFirst(DAY_EVENTS_SELECTOR);
        } catch (NoSuchElementException e) {
            return false;
        }

        return !eventList.find(EVENT_SELECTOR).isEmpty();
    }

    public boolean serviceRepeated(int day, String repetitionTmp, int daysAfter) {

        Repetition repetition = parseRepetition(repetitionTmp);

        List<Integer> repeatedDayList = new ArrayList<>();

        switch (repetition) {
            case DAILY:
                while (day < MAX_DAY_OF_MONTH) {
                    repeatedDayList.add(day);
                    day++;
                }
                break;
            case WEEKLY:
                while (day < MAX_DAY_OF_MONTH) {
                    repeatedDayList.add(day);
                    day += 7;
                }
            default:
                break;
        }

        FluentWebElement dayCell = null;

        FluentWebElement eventList = null;
        for (int repeatedDay : repeatedDayList) {
            dayCell = findFirst(DAY_CELL_SELECTOR, FilterConstructor.withText(String.valueOf(repeatedDay)));
            try {
                eventList = dayCell.findFirst(DAY_EVENTS_SELECTOR);
                if (eventList.find(EVENT_SELECTOR).isEmpty()) {
                    return false;
                }
            } catch (NoSuchElementException e) {
                return false;
            }
        }

        return true;
    }

    private Repetition parseRepetition(String repetitionTmp) {

        switch (repetitionTmp) {
            case "Daily":
                return Repetition.DAILY;
            case "Weekly":
                return Repetition.WEEKLY;
        }
        return null;
    }

}
