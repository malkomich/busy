package busy.schedule;

import static org.junit.Assert.assertFalse;
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
 * Test Case for the ScheduleDao implementation class.
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
    private static final int[] INVALID_WEEKS = {1,10};
    
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
        
        List<YearSchedule> yearSchedules = repository.findByBranchAndYear(wrongBranch, YEAR);
        assertTrue(yearSchedules.isEmpty());
    }
	
	@Test
    public void findYearScheduleByInvalidYear() {
        
        List<YearSchedule> yearSchedules = repository.findByBranchAndYear(branch, INVALID_YEAR);
        assertTrue(yearSchedules.isEmpty());
    }
	
	@Test
	public void findYearScheduleByBranchAndYearSuccessfully() {
		
		List<YearSchedule> yearSchedules = repository.findByBranchAndYear(branch, YEAR);
		assertFalse(yearSchedules.isEmpty());
	}
	
	@Test
    public void findWeekScheduleByInvalidYearSchedule() {
        
        List<WeekSchedule> weekSchedules = repository.findByYearAndWeeks(INVALID_YEAR, WEEKS);
        assertTrue(weekSchedules.isEmpty());
    }
	
	@Test
    public void findWeekScheduleByInvalidWeeksSchedule() {
        
        List<WeekSchedule> weekSchedules = repository.findByYearAndWeeks(YEAR, INVALID_WEEKS);
        assertTrue(weekSchedules.isEmpty());
    }
    
    @Test
    public void findWeekScheduleByYearAndWeeksSuccessfully() {
        
        List<WeekSchedule> weekSchedules = repository.findByYearAndWeeks(YEAR, WEEKS);
        assertFalse(weekSchedules.isEmpty());
    }
    
    @Test
    public void findDefaultWeekScheduleFail() {
        
        WeekSchedule defaultWeek = repository.findDefaultWeek();
        assertNull(defaultWeek);
    }
    
    @Test
    @DatabaseSetup("../schedule/defaultWeekSet.xml")
    public void findDefaultWeekScheduleSuccessfully() {
        
        WeekSchedule defaultWeek = repository.findDefaultWeek();
        assertNotNull(defaultWeek);
    }
	
}
