package busy.bdd.admin.verify_company;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the 'Verify Company' feature, which gherkin definition is in the path
 * specified by features parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result
 * tests, and the destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/admin_can_verify_a_company.feature", plugin = {
		"html:target/cucumber" }, glue = "classpath:busy/bdd/admin/verify_company", snippets = SnippetType.UNDERSCORE)
public class ApproveCompanyTest {

}
