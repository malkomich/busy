package busy.schedule;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import busy.company.Branch;
import busy.company.Company;
import busy.util.OperationResult;

/**
 * Schedule service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private MessageSource messageSource;

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

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#findServiceTypesByCompany(busy.company.Company)
     */
    @Override
    public List<ServiceType> findServiceTypesByCompany(Company company) {

        return serviceTypeDao.findByCompany(company);
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#deleteServiceTypeById(int)
     */
    @Override
    public OperationResult deleteServiceType(ServiceType serviceType) {

        OperationResult result = serviceTypeDao.delete(serviceType);

        if (!result.isSuccess()) {
            Locale locale = LocaleContextHolder.getLocale();
            String errorMsg = messageSource.getMessage(result.getCode().getMsgCode(), null, locale);
            result.setErrorMsg(errorMsg);
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#findServiceType(busy.company.Company, java.lang.String)
     */
    @Override
    public ServiceType findServiceType(Company company, String name) {

        return serviceTypeDao.findByCompanyAndName(company, name);
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#saveServiceType(busy.schedule.ServiceType)
     */
    @Override
    public ServiceType saveServiceType(ServiceType sType) {

        return serviceTypeDao.save(sType);
    }

}
