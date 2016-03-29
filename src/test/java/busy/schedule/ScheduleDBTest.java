package busy.schedule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.company.Branch;
import busy.util.SecureSetter;

/**
 * Test Cases for the ScheduleDao implementation class.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("../company/categorySet.xml")
@DatabaseSetup("../company/companySet.xml")
@DatabaseSetup("../location/countrySet.xml")
@DatabaseSetup("../location/citySet.xml")
@DatabaseSetup("../location/addressSet.xml")
@DatabaseSetup("../company/branchSet.xml")
@DatabaseSetup("../schedule/scheduleSet.xml")
public class ScheduleDBTest extends AbstractDBTest {

    private static final int YEAR = 2016;
    private static final int INVALID_YEAR = 0;

    private static final int[] WEEKS = {1};
    private static final int[] INVALID_WEEKS = {10};
    private static final int[] PARTIALLY_VALID_WEEKS = {1, 10};

    @Autowired
    private ScheduleDaoImpl repository;

    private Branch branch;

    @Before
    public void setUp() {

        branch = new Branch();
        SecureSetter.setId(branch, 1);
    }

    @Test
    public void findYearScheduleByInvalidBranch() {

        Branch wrongBranch = new Branch();
        SecureSetter.setId(wrongBranch, INVALID_ID);

        YearSchedule yearSchedule = repository.findYearFromBranch(wrongBranch, YEAR);
        assertNull(yearSchedule);
    }

    @Test
    public void findYearScheduleByInvalidYear() {

        YearSchedule yearSchedule = repository.findYearFromBranch(branch, INVALID_YEAR);
        assertNull(yearSchedule);
    }

    @Test
    public void findYearScheduleByBranchAndYearSuccessfully() {

        YearSchedule yearSchedule = repository.findYearFromBranch(branch, YEAR);
        assertNotNull(yearSchedule);
    }

    @Test
    public void findWeekSchedulesByInvalidBranch() {

        Branch wrongBranch = new Branch();
        SecureSetter.setId(wrongBranch, INVALID_ID);

        List<WeekSchedule> weekSchedules = repository.findWeeksFromBranch(wrongBranch, YEAR, WEEKS);
        assertTrue(weekSchedules.isEmpty());
    }

    @Test
    public void findWeekSchedulesByInvalidYear() {

        List<WeekSchedule> weekSchedules = repository.findWeeksFromBranch(branch, INVALID_YEAR, WEEKS);
        assertTrue(weekSchedules.isEmpty());
    }

    @Test
    public void findWeekSchedulesByInvalidWeeks() {

        List<WeekSchedule> weekSchedules = repository.findWeeksFromBranch(branch, YEAR, INVALID_WEEKS);
        assertTrue(weekSchedules.isEmpty());
    }

    @Test
    public void findWeekSchedulesByPartiallyValidWeeks() {

        List<WeekSchedule> weekSchedules = repository.findWeeksFromBranch(branch, YEAR, PARTIALLY_VALID_WEEKS);
        assertTrue(weekSchedules.size() == 1);
    }

    @Test
    public void findWeekSchedulesByBranchAndYearAndWeeksSuccessfully() {

        List<WeekSchedule> weekSchedules = repository.findWeeksFromBranch(branch, YEAR, WEEKS);
        assertTrue(weekSchedules.size() == 1);
    }

    @Test
    public void findDefaultWeekScheduleFail() {

        WeekSchedule defaultWeek = repository.findDefaultWeek(branch);
        assertNull(defaultWeek);
    }

    @Test
    @DatabaseSetup("../schedule/defaultWeekSet.xml")
    public void findDefaultWeekScheduleByInvalidBranch() {

        Branch wrongBranch = new Branch();
        SecureSetter.setId(wrongBranch, INVALID_ID);

        WeekSchedule defaultWeek = repository.findDefaultWeek(wrongBranch);
        assertNotNull(defaultWeek);
    }

    @Test
    @DatabaseSetup("../schedule/defaultWeekSet.xml")
    public void findDefaultWeekScheduleSuccessfully() {

        WeekSchedule defaultWeek = repository.findDefaultWeek(branch);
        assertNotNull(defaultWeek);
    }

}