package busy;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Basic class configurated to let subclasses easily automate integration
 * testing using a library called 'FluentLenium' to take advantage of Selenium
 * API.
 * 
 * @author malkomich
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class AbstractFunctionalTest extends FluentTest {

	/**
	 * Absolute path of the root URL, to let from now the use of relative URL
	 * paths.
	 */
	@Value("${web.rootUrl}")
	private String rootUrl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fluentlenium.core.Fluent#goTo(org.fluentlenium.core.FluentPage)
	 */
	@Override
	public <P extends FluentPage> P goTo(P page) {
		((BusyPage) page).setUrl(rootUrl);
		return super.goTo(page);
	}

	@PostConstruct
	public void setUp() {
		this.initFluent(new HtmlUnitDriver());
		this.initTest();
	}

	@PreDestroy
	public void tearDown() {
		this.quit();
	}
}
