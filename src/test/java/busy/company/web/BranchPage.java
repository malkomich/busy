package busy.company.web;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;

import busy.BusyPage;

/**
 * Branch Page
 * 
 * @author malkomich
 *
 */
public class BranchPage extends BusyPage {

    private static final String PARTIAL_PATH = "/company/"; // "/company/{id}/branch/{id}"
    private static final String DESCRIPTION = "Branch Page";

    /*
     * CSS Selectors
     */
    private static final String CALENDAR_MONTH_SELECTOR = ".cal-month-box";
    private static final String CALENDAR_DAY_SELECTOR = "#cal-day-box";
    private static final String DAY_MODE_SELECTOR = "[data-calendar-view='day']";

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

    public BranchPage selectDayInCalendar() {

        find(DAY_MODE_SELECTOR).click();
        return this;
    }

    public boolean calendarDayShown() {

        return findFirst(CALENDAR_DAY_SELECTOR).isDisplayed();
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
                        System.out.println(item.findFirst(SERVICE_TYPE_PARAMS_SELECTOR[0]).getAttribute("title") + " MUSTS CONTAIN " + serviceProperties[i]);
                        matches++;
                    }
                } else if (item.findFirst(SERVICE_TYPE_PARAMS_SELECTOR[i]).getText().contains(serviceProperties[i])) {
                    System.out.println(item.findFirst(SERVICE_TYPE_PARAMS_SELECTOR[i]).getText() + " MUSTS CONTAIN " + serviceProperties[i]);
                    matches++;
                } else 
                    System.out.println(item.findFirst(SERVICE_TYPE_PARAMS_SELECTOR[i]).getText() + " MUSTS CONTAIN " + serviceProperties[i]);
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

    public boolean formIsShown() {

        return findFirst(SERVICE_TYPE_FORM_SELECTOR).isDisplayed();
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

    public BranchPage submitServiceType() {

        submit(SERVICE_TYPE_FORM_SUBMIT_SELECTOR);
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean duplicateServiceTypeErrorShown() {

        return !find(SERVICE_TYPE_FORM_ERROR_SELECTOR).isEmpty();
    }

}
