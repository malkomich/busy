package busy.bdd.company.search_by_name;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the feature of searching a Company by his name, which gherkin
 * definition is in the path specified by features parameter in @CucumberOptions
 * annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result
 * tests, and the destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/i_can_search_company_by_name.feature", plugin = {
		"html:target/cucumber" }, glue = "classpath:busy/bdd/company/search_by_name", snippets = SnippetType.UNDERSCORE)
public class SearchCompanyByNameTest {

}
