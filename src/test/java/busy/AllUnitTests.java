package busy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.postgresql.core.Notification;

import busy.company.CompanyTests;
import busy.location.LocationTests;
import busy.notifications.NotificationTests;
import busy.role.RoleTests;
import busy.user.UserTests;

@RunWith(Suite.class)
@SuiteClasses({LocationTests.class, UserTests.class, CompanyTests.class, RoleTests.class, NotificationTests.class})
public class AllUnitTests {

}
