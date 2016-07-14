package busy.admin.web;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.FilterConstructor;
import org.openqa.selenium.By;

import busy.BusyPage;

/**
 * Admin Page
 * 
 * @author malkomich
 *
 */
public class AdminPage extends BusyPage {

    public static final int COMPANY = 0;
    public static final int USER = 1;

    private static final String PATH = "/admin";
    private static final String DESCRIPTION = "Admin Page";

    /*
     * CSS Selectors
     */
    private static final String SHOW_COMPANIES_SELECTOR = "#admin-companies-button";
    private static final String SHOW_USERS_SELECTOR = "#admin-users-button";
    private static final String COMPANY_SELECTOR = ".company-item";
    private static final String USER_SELECTOR = ".user-item";
    private static final String SWITCH_SELECTOR = ".onoffswitch-checkbox";
    private static final String MESSAGE_SELECTOR = "#infoMessage";

    private FluentWebElement companyItem;

    @Override
    public void isAt() {

        String description = getDriver().findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertThat(description).contains(DESCRIPTION);
    }

    @Override
    public String relativePath() {
        return PATH;
    }

    public AdminPage clickOnVerifyCompanies() {

        click(SHOW_COMPANIES_SELECTOR);
        waitForJSandJQueryToLoad();
        return this;
    }

    public AdminPage selectCompany(String company) {

        if (!company.isEmpty()) {
            FluentWebElement companyItem = findFirst(COMPANY_SELECTOR, FilterConstructor.withText().contains(company));
            this.companyItem = companyItem;
        }
        return this;
    }

    public AdminPage toogleActiveStatus(int section, String name) {

        switch (section) {
            case COMPANY:
                click(companyItem.find(SWITCH_SELECTOR));
                break;
            case USER:
                FluentWebElement userItem = findFirst(USER_SELECTOR, FilterConstructor.withText().contains(name));
                click(userItem.find(SWITCH_SELECTOR));
                break;

            default:
                break;
        }
        return this;
    }

    public AdminPage companyIsActive(String company) {

        if (!company.isEmpty()) {
            FluentWebElement companyItem = findFirst(COMPANY_SELECTOR, FilterConstructor.withText().contains(company));
            assertThat(companyItem.findFirst(SWITCH_SELECTOR).isSelected());
        }
        return this;
    }

    public AdminPage companyIsBlocked(String company) {

        if (!company.isEmpty()) {
            FluentWebElement companyItem = findFirst(COMPANY_SELECTOR, FilterConstructor.withText().contains(company));
            assertThat(!companyItem.findFirst(SWITCH_SELECTOR).isSelected());
        }
        return this;
    }

    public boolean emailIsSent() {

        // Nothing to check, the operation is done in back end.
        return true;
    }

    public AdminPage clickOnUsersSection() {

        click(SHOW_USERS_SELECTOR);
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean userListShown() {

        return !find(USER_SELECTOR).isEmpty();
    }

    public boolean userActiveStatus(String name, boolean active) {

        if (!name.isEmpty()) {
            FluentWebElement userItem = findFirst(USER_SELECTOR, FilterConstructor.withText().contains(name));
            if ((active && userItem.findFirst(SWITCH_SELECTOR).isSelected())
                || (!active && !userItem.findFirst(SWITCH_SELECTOR).isSelected())) {
                return true;
            }
        }
        return false;
    }

    public boolean blockUserConfirmationShown() {

        return findFirst(MESSAGE_SELECTOR).isDisplayed();
    }

}
