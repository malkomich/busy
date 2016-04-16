package busy.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import busy.company.Branch;
import busy.company.Company;

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
    
    @Autowired
    private ServiceTypeDao serviceTypeDao;

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }
    
    public void setServiceTypeDao(ServiceTypeDao serviceTypeDao) {
        this.serviceTypeDao = serviceTypeDao;
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#findScheduleByBranch(busy.company.Branch)
     */
    @Override
    public YearSchedule findScheduleByBranch(Branch branch, int year) {

        return scheduleDao.findYearFromBranch(branch, year);
    }

    /* (non-Javadoc)
     * @see busy.schedule.ScheduleService#findServiceTypesByCompany(busy.company.Company)
     */
    @Override
    public List<ServiceType> findServiceTypesByCompany(Company company) {

        return serviceTypeDao.findByCompany(company);
    }

}
