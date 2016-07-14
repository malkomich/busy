package busy.bdd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import busy.bdd.accounts.log_in.LogInTest;
import busy.bdd.accounts.log_out.LogOutTest;
import busy.bdd.accounts.sign_up.SignUpTest;
import busy.bdd.admin.block_users.BlockUserTest;
import busy.bdd.admin.verify_company.ApproveCompanyTest;
import busy.bdd.bookings.new_booking.NewBookingTest;
import busy.bdd.company.create_roles.CreateRolesTest;
import busy.bdd.company.create_services.CreateServicesTest;
import busy.bdd.company.manage_service_types.ManageServiceTypesTest;
import busy.bdd.company.register.RegisterCompanyTest;
import busy.bdd.company.schedule_view.SeeOwnCompanyScheduleTest;
import busy.bdd.company.search_by_name.SearchCompanyByNameTest;

/**
 * Execute all BDD Tests covering all the User Stories
 * 
 * @author malkomich
 *
 */
@RunWith(Suite.class)
@SuiteClasses({LogInTest.class, SignUpTest.class, LogOutTest.class, RegisterCompanyTest.class, ApproveCompanyTest.class,
    SearchCompanyByNameTest.class, SeeOwnCompanyScheduleTest.class, ManageServiceTypesTest.class,
    CreateServicesTest.class, NewBookingTest.class, CreateRolesTest.class, BlockUserTest.class})
public class RunStories {

}
