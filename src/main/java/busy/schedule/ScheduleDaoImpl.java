package busy.schedule;

import java.util.List;

import org.springframework.stereotype.Repository;

import busy.company.Branch;

/**
 * Schedule persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findByBranchAndYear(busy.company.Branch, int)
     */
    @Override
    public YearSchedule findYearFromBranch(Branch branch, int year) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findByYearAndWeeks(int, int[])
     */
    @Override
    public List<WeekSchedule> findWeeksFromBranch(Branch branch, int year, int... weeks) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findDefaultWeek()
     */
    @Override
    public WeekSchedule findDefaultWeek(Branch branch) {
        // TODO Auto-generated method stub
        return null;
    }

}