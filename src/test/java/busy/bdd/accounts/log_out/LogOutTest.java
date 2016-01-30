package busy.bdd.accounts.log_out;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the Log In feature, which gherkin definition is in the path specified
 * by features parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result
 * tests, and the destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/i_can_log_out.feature", plugin = {
		"html:target/cucumber" }, glue = "classpath:busy/bdd/accounts/log_out", snippets = SnippetType.UNDERSCORE)
public class LogOutTest {

}