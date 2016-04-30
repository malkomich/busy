package busy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gargoylesoftware.htmlunit.WebClient;

import busy.util.UriUtils;

/**
 * Basic class configurated to let subclasses easily automate integration testing using a library
 * called 'FluentLenium' to take advantage of Selenium API.
 * 
 * @author malkomich
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class AbstractFunctionalTest extends FluentTest {

    @Autowired
    protected JdbcTemplate template;

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private Environment env;
    
    /*
     * (non-Javadoc)
     * @see org.fluentlenium.core.Fluent#goTo(org.fluentlenium.core.FluentPage)
     */
    @Override
    public <P extends FluentPage> P goTo(P page) {
        String rootPath = UriUtils.getRootPath(env);
        ((BusyPage) page).setUrl(rootPath);
        return super.goTo(page);
    }

    @PostConstruct
    public void setUp() {
        initFluent(new BusyDriver(true));
        this.initTest();
    }

    @PreDestroy
    public void tearDown() {
        this.quit();
    }

    protected String getSQLScript(String path) {

        Resource resource = context.getResource(path);
        BufferedReader reader = null;
        StringBuilder stBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
                stBuilder.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stBuilder.toString();
    }

    protected String getCurrentContextHandle() {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod((RemoteWebDriver) getDriver());
        String context = (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        return context;
    }
}

class BusyDriver extends HtmlUnitDriver {

    public BusyDriver() {
        super();
    }

    public BusyDriver(boolean enableJavascript) {
        super(enableJavascript);
    }

    @Override
    protected WebClient modifyWebClient(WebClient client) {
        super.modifyWebClient(client);
        client.getOptions().setThrowExceptionOnScriptError(false);
        // Needed to disable CSS due to a problem with 'collapse' divs behaviour
        client.getOptions().setCssEnabled(false);
        return client;
    }

}
