package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.FilterConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;

import busy.BusyPage;

/**
 * Page for the main page of Busy.
 * 
 * @author malkomich
 *
 */
public class MainPage extends BusyPage {

    private static final String PATH = "/";
    private static final String DESCRIPTION = "Main Page";

    /*
     * CSS Selectors
     */
    private static final String TOOGLE_MENU_SELECTOR = "#toggle-menu-button";
    private static final String USER_MENU_SELECTOR = "#userMenu";
    private static final String LOGOUT_SELECTOR = "#logout-link";
    private static final String CREATE_COMPANY_SELECTOR = "#create-company";
    private static final String BUSINESS_SECTION_SELECTOR = "#select-role";
    private static final String BUSINESS_SECTION_ITEM_SELECTOR = ".role-select-menu-item";
    private static final String SEARCHBAR_SELECTOR = ".search-bar";
    private static final String SEARCHBAR_TEXT_SELECTOR = ".search-bar-text";
    private static final String SEARCH_LIST_ITEM_SELECTOR = ".autocomplete-suggestion";

    private static final String MESSAGE_SELECTOR = "#infoMessage";
    private static final String ITEM_NOTIFICATION_MESSAGE_SELECTOR = "div.item-notification-message";

    /*
     * Others
     */
    private static final String APPROVED = "approved";
    private static final String REJECTED = "rejected";

    /*
     * (non-Javadoc)
     * @see busy.BusyPage#relativePath()
     */
    @Override
    public String relativePath() {
        return PATH;
    }

    /*
     * (non-Javadoc)
     * @see busy.BusyPage#isAt()
     */
    @Override
    public void isAt() {

        String description = getDriver().findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertThat(description).contains(DESCRIPTION);
    }

    /**
     * Clicks on the link which function is log out of the session account.
     * 
     * @return
     */
    public MainPage clickOnLogOut() {

        try {
            click(USER_MENU_SELECTOR);
        } catch (ElementNotVisibleException e) {
            click(TOOGLE_MENU_SELECTOR);
            click(USER_MENU_SELECTOR);
        }

        click(LOGOUT_SELECTOR);
        return this;
    }

    public MainPage clickOnCreateCompany() {

        click(CREATE_COMPANY_SELECTOR);
        return this;
    }

    public boolean companyPendingMessageIsShown() {

        waitForJSandJQueryToLoad();

        return findFirst(MESSAGE_SELECTOR).isDisplayed();
    }

    public boolean companyApprovedNotificationIsShown() {

        for (FluentWebElement notification : find(ITEM_NOTIFICATION_MESSAGE_SELECTOR)) {
            if (notification.getText().contains(APPROVED)) {
                return true;
            }
        }
        return false;
    }

    public boolean businessSectionIsShown() {

        return findFirst(BUSINESS_SECTION_SELECTOR).isDisplayed();
    }

    public boolean companyRejectedNotificationIsShown() {

        for (FluentWebElement notification : find(ITEM_NOTIFICATION_MESSAGE_SELECTOR)) {
            if (notification.getText().contains(REJECTED)) {
                return true;
            }
        }
        return false;
    }

    public MainPage clickOnSearchBar() {

        click(SEARCHBAR_SELECTOR);
        return this;
    }

    public MainPage fillSearchBar(String companyName) {

        fill(SEARCHBAR_TEXT_SELECTOR).with(companyName);
        waitForJSandJQueryToLoad();
        return this;
    }

    public boolean isCompanyInSearchList(String companyName) {

        FluentList<FluentWebElement> list = find(SEARCH_LIST_ITEM_SELECTOR);
        for (FluentWebElement item : list) {
            if (item.getText().contains(companyName)) {
                return true;
            }
        }
        return false;
    }

    public MainPage selectCompanyInSearchList(String companyName) {

        FluentWebElement item = findFirst(SEARCH_LIST_ITEM_SELECTOR, FilterConstructor.withText(companyName));
        click(item);
        return this;
    }

    public boolean isSearchListShown() {

        try {
            if (findFirst(SEARCH_LIST_ITEM_SELECTOR).isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException e) {
            // Nothing to do, element just do not exists
        }

        return false;
    }

    public MainPage selectCompanySection(String companyName) {

        FluentWebElement dropdowm = findFirst(BUSINESS_SECTION_SELECTOR);
        dropdowm.click();

        FluentWebElement companySelector =
                findFirst(BUSINESS_SECTION_ITEM_SELECTOR, FilterConstructor.withText().contains(companyName));

        if (companySelector == null) {
            throw new AssertionError("Selector for company " + companyName + " not found.");
        }
        companySelector.click();

        return this;
    }

}
