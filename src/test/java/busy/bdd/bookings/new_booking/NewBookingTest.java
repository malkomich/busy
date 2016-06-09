package busy.bdd.bookings.new_booking;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the feature about booking a service, which gherkin definition is in the path specified by
 * features parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result tests, and the
 * destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/i_can_book_a_service.feature", plugin = {"html:target/cucumber"},
    glue = "classpath:busy/bdd/bookings/new_booking", snippets = SnippetType.UNDERSCORE)
public class NewBookingTest {

}
