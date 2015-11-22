package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;

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
	
	@Override
	public String relativePath() {
		return PATH;
	}

	@Override
	public void isAt() {
		String description = getDriver().findElement(By.xpath("//meta[@name='description']"))
				.getAttribute("content");
		assertThat(description).contains(DESCRIPTION);
	}
	
}
