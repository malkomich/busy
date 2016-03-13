package busy.bdd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import busy.bdd.accounts.log_in.LogInTest;
import busy.bdd.accounts.log_out.LogOutTest;
import busy.bdd.accounts.sign_up.SignUpTest;
import busy.bdd.admin.verify_company.ApproveCompanyTest;
import busy.bdd.company.register.RegisterCompanyTest;

/**
 * Execute all BDD Tests covering all the User Stories
 * 
 * @author malkomich
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ LogInTest.class, SignUpTest.class, LogOutTest.class, RegisterCompanyTest.class, ApproveCompanyTest.class })
public class RunStories {

}
