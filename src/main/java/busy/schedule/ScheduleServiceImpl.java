package busy.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import busy.company.Branch;

/**
 * Schedule service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#findScheduleByBranch(busy.company.Branch)
     */
    @Override
    public YearSchedule findScheduleByBranch(Branch branch, int year) {

        return scheduleDao.findYearFromBranch(branch, year);
    }

}
