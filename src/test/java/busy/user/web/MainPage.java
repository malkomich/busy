package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

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
		
		try{
			click(USER_MENU_SELECTOR);
		} catch(ElementNotVisibleException e) {
			click(TOOGLE_MENU_SELECTOR);
			click(USER_MENU_SELECTOR);
		}
		
		click(LOGOUT_SELECTOR);
		return this;
	}

	public void clickOnCreateCompany() {
		// TODO Auto-generated method stub
		
	}

	public boolean companyPendingMessageIsShown() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean companyApprovedNotificationIsShown() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean businessSectionIsShown() {
		// TODO Auto-generated method stub
		return false;
	}

}
