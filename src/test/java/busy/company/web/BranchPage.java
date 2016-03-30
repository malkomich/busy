package busy.company.web;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;

import busy.BusyPage;

/**
 * Company Info Page
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
    private static final String CALENDAR_DAY_SELECTOR = ".cal-day-box";
    private static final String DAY_MODE_SELECTOR = ".cal-month-day";

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

        return find(CALENDAR_MONTH_SELECTOR).first().isDisplayed();
    }

    public BranchPage selectDayInCalendar() {

        find(DAY_MODE_SELECTOR).click();
        return this;
    }

    public boolean calendarDayShown() {

        return find(CALENDAR_DAY_SELECTOR).first().isDisplayed();
    }

}
