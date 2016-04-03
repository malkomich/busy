package busy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import busy.booking.BookingTests;
import busy.company.CompanyTests;
import busy.location.LocationTests;
import busy.notifications.NotificationTests;
import busy.role.RoleTests;
import busy.schedule.ScheduleTests;
import busy.user.UserTests;

@RunWith(Suite.class)
@SuiteClasses({ LocationTests.class, UserTests.class, CompanyTests.class, RoleTests.class, NotificationTests.class, ScheduleTests.class, BookingTests.class })
public class AllUnitTests {

}
