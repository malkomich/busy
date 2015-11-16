package busy.bdd;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Execute all BDD Features in the path given to the "glue" parameter
 * in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result
 * tests, and the destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = "busy.bdd", plugin = {"html:target/cucumber"})
public class RunStories {

}