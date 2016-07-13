package busy.bdd.admin.block_users;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Execute the 'Block User' feature, which gherkin definition is in the path specified by
 * features parameter in @CucumberOptions annotation.
 * 
 * The "plugin" parameter of @CucumberOptions specifies the format of the result tests, and the
 * destination path of them.
 * 
 * @author malkomich
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:busy/bdd/admin_can_block_a_user.feature",
        plugin = { "html:target/cucumber" }, glue = "classpath:busy/bdd/admin/block_users",
        snippets = SnippetType.UNDERSCORE)
public class BlockUserTest {

}
