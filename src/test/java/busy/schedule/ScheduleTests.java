package busy.schedule;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ServiceDBTest.class, ServiceTypeDBTest.class, ServiceTypeTest.class, ScheduleDBTest.class, BookingDBTest.class})
public class ScheduleTests {

}
