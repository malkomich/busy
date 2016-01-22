package busy;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Extension of FluentPage which enable relative path delegating to Spring the
 * assignment of the root path of the URL based on the deployment Profile.
 * 
 * @author malkomich
 *
 */
public abstract class BusyPage extends FluentPage {

	private String url;

	public void setUrl(String rootUrl) {
		String path = relativePath();
		if (path == null)
			path = "/";
		url = rootUrl + relativePath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fluentlenium.core.FluentPage#getUrl()
	 */
	@Override
	public String getUrl() {
		return url;
	}

	public abstract String relativePath();

	protected boolean waitForJSandJQueryToLoad() {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

}
