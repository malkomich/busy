package busy.bdd.company.register;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the Register Company feature, which gherkin definition is in the path specified by
 * features parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result tests, and the
 * destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/i_can_register_a_company.feature", plugin = { "html:target/cucumber" },
        glue = "classpath:busy/bdd/company/register", snippets = SnippetType.UNDERSCORE)
public class RegisterCompanyTest {

}
