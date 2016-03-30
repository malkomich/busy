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
public class CompanyPage extends BusyPage {

    private static final String PARTIAL_PATH = "/company/";
    private static final String DESCRIPTION = "Company Page";

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
        // TODO Auto-generated method stub
        return false;
    }

    public CompanyPage selectDayInCalendar() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean calendarDayShown() {
        // TODO Auto-generated method stub
        return false;
    }

}
