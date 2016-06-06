package busy.bdd.company.create_services;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the feature about creation of new services, which gherkin definition is in the path
 * specified by features parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result tests, and the
 * destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/i_can_create_services.feature", plugin = {"html:target/cucumber"},
        glue = "classpath:busy/bdd/company/create_services", snippets = SnippetType.UNDERSCORE)
public class CreateServicesTest {

}
