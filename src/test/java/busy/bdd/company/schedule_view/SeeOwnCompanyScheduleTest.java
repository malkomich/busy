package busy.bdd.company.schedule_view;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the feature of the right view of the company schedule by his manager, which gherkin
 * definition is in the path specified by features parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result tests, and the
 * destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/i_can_see_my_company_schedule.feature",
        plugin = {"html:target/cucumber"}, glue = "classpath:busy/bdd/company/schedule_view",
        snippets = SnippetType.UNDERSCORE)
public class SeeOwnCompanyScheduleTest {

}
