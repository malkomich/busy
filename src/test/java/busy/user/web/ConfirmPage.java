package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;

import busy.BusyPage;

/**
 * Page for the Email Confirmation
 * @author malkomich
 *
 */
public class ConfirmPage extends BusyPage {

	private static final String PATH = "confirm";
	private static final String DESCRIPTION = "Confirm Page";

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
	
}
