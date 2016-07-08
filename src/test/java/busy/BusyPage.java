package busy;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

/**
 * Extension of FluentPage which enable relative path delegating to Spring the assignment of the
 * root path of the URL based on the deployment Profile.
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
     * @see org.fluentlenium.core.FluentPage#getUrl()
     */
    @Override
    public String getUrl() {
        return url;
    }

    /*
     * (non-Javadoc)
     * @see org.fluentlenium.core.FluentPage#isAt()
     */
    @Override
    public abstract void isAt();

    /**
     * Gets the relative path which appended to the root URL retrieves the Page.
     * 
     * @return The URL path for the page
     */
    public abstract String relativePath();

    /**
     * Wait until all jQuery and plain old JS scripts has finished.
     * 
     * @return
     */
    protected boolean waitForJSandJQueryToLoad() {

        // Configure time to wait till the conditions are fulfilled
        Wait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(30, TimeUnit.SECONDS)
            .ignoring(NoSuchElementException.class).pollingEvery(2, TimeUnit.SECONDS);

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

    protected FluentWebElement getWhenVisible(String id) {

        Wait<BusyDriver> wait = new FluentWait<BusyDriver>((BusyDriver) getDriver()).withTimeout(10, TimeUnit.SECONDS)
            .pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

        FluentWebElement element = wait.until(new Function<WebDriver, FluentWebElement>() {

            public FluentWebElement apply(WebDriver driver) {
                return new FluentWebElement(driver.findElement(By.id(id)));
            }
        });
        return element;
    }

}
