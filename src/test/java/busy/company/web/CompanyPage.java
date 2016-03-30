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

    private static final String PARTIAL_PATH = "/company/"; // "/company/{id}"
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

}
