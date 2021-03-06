package busy.bdd.accounts.sign_up;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.SnippetType;

/**
 * Execute the Sign Up feature, which gherkin definition is in the path specified by features
 * parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result tests, and the
 * destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/i_can_sign_up.feature", plugin = { "html:target/cucumber" },
        glue = "classpath:busy/bdd/accounts/sign_up", snippets = SnippetType.UNDERSCORE)
public class SignUpTest {

}
