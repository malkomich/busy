package busy.company;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({BranchDBTest.class, CategoryDBTest.class, CompanyDBTest.class})
public class CompanyTests {

}
