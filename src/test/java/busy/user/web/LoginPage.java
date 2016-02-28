package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;

import busy.BusyPage;

/**
 * Page for the user login.
 * 
 * @author malkomich
 *
 */
public class LoginPage extends BusyPage {

	private static final String PATH = "/login";
	private static final String DESCRIPTION = "Login Page";

	/*
	 * CSS Selectors
	 */
	private static final String EMAIL_SELECTOR = "#email";
	private static final String PASSWORD_SELECTOR = "#password";
	
	private static final String SUBMIT_SELECTOR = "#submit";
	private static final String ERROR_SELECTOR = "label.error";
	
	private static final String SIGNUP_SELECTOR = "#signupButton";
	private static final String SUCCESS_MESSAGE_SELECTOR = "#signupConfirmation";
	private static final String VALIDATION_MESSAGE_SELECTOR = "#validationMessage";

	@Override
	public String relativePath() {

		return PATH;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fluentlenium.core.FluentPage#isAt()
	 */
	@Override
	public void isAt() {

		String description = getDriver().findElement(By.xpath("//meta[@name='description']"))
				.getAttribute("content");
		assertThat(description).contains(DESCRIPTION);
	}

	public LoginPage setEmail(String email) {

		fill(EMAIL_SELECTOR).with(email);
		return this;
	}

	public LoginPage setPassword(String password) {

		fill(PASSWORD_SELECTOR).with(password);
		return this;
	}

	public LoginPage submit() {

		submit(SUBMIT_SELECTOR);
		return this;
	}

	public boolean errorIsShown() {

		return !find(ERROR_SELECTOR).isEmpty();
	}
	
	public LoginPage clickSignUp() {
		submit(SIGNUP_SELECTOR);
		return this;
	}
	
	public boolean signupSuccessMessageIsShown() {

		waitForJSandJQueryToLoad();
		
		return findFirst(SUCCESS_MESSAGE_SELECTOR).isDisplayed();
	}

	public boolean validationMessageIsShown() {
		
		waitForJSandJQueryToLoad();
		
		return findFirst(VALIDATION_MESSAGE_SELECTOR).isDisplayed();
	}

}
