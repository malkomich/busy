package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;

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

	private static final String MESSAGE_SELECTOR = "#infoMessage";
	private static final String NOTIFICATIONS_SWITCH_SELECTOR = "#notifications-switch";
	private static final String NOTIFICATIONS_SELECTOR = ".notifications-content";
	private static final String ITEM_NOTIFICATION_SELECTOR = ".item-notification-content";
	private static final String ITEM_NOTIFICATION_MESSAGE_SELECTOR = "div.item-notification-message";

	/*
	 * Others
	 */
	private static final String APPROVED = "approved";
	private static final String REJECTED = "rejected";

	@Override
	public String relativePath() {
		return PATH;
	}

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

	public void clickOnCreateCompany() {

		click(CREATE_COMPANY_SELECTOR);
	}

	public boolean companyPendingMessageIsShown() {

		waitForJSandJQueryToLoad();

		return findFirst(MESSAGE_SELECTOR).isDisplayed();
	}

	public boolean companyApprovedNotificationIsShown() {

		try {
			click(NOTIFICATIONS_SWITCH_SELECTOR);
		} catch (ElementNotVisibleException e) {
			click(TOOGLE_MENU_SELECTOR);
			click(NOTIFICATIONS_SWITCH_SELECTOR);
		}

		waitForJSandJQueryToLoad();

		FluentList<FluentWebElement> items = find(NOTIFICATIONS_SELECTOR).first().find(ITEM_NOTIFICATION_SELECTOR);
		boolean rightMessage = find(ITEM_NOTIFICATION_MESSAGE_SELECTOR).first().getText().contains(APPROVED);
		
		return items.size() == 1 && rightMessage;
	}

	public boolean businessSectionIsShown() {

		return false;
	}

	public boolean companyRejectedNotificationIsShown() {

		try {
			click(NOTIFICATIONS_SWITCH_SELECTOR);
		} catch (ElementNotVisibleException e) {
			click(TOOGLE_MENU_SELECTOR);
			click(NOTIFICATIONS_SWITCH_SELECTOR);
		}

		waitForJSandJQueryToLoad();

		FluentList<FluentWebElement> items = find(NOTIFICATIONS_SELECTOR).first().find(ITEM_NOTIFICATION_SELECTOR);
		boolean rightMessage = find(ITEM_NOTIFICATION_MESSAGE_SELECTOR).first().getText().contains(REJECTED);
		
		return items.size() == 1 && rightMessage;
	}

}
