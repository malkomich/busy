package busy.bdd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import busy.bdd.accounts.log_in.LogInTest;
import busy.bdd.accounts.sign_up.SignUpTest;

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
@RunWith(Suite.class)
@SuiteClasses({ LogInTest.class, SignUpTest.class })
public class RunStories {

}
