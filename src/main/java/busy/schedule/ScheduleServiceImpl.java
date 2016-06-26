package busy.schedule;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import busy.company.Company;
import busy.role.Role;
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
    private ServiceTypeDao serviceTypeDao;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private TimeSlotDao timeSlotDao;
    
    @Autowired
    private ScheduleDao scheduleDao;

    public void setServiceTypeDao(ServiceTypeDao serviceTypeDao) {
        this.serviceTypeDao = serviceTypeDao;
    }

    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public void setSscheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#findServiceTypesByCompany(busy.company.Company)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceType> findServiceTypesByCompany(Company company) {

        return serviceTypeDao.findByCompany(company);
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#deleteServiceTypeById(int)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(readOnly = true)
    public ServiceType findServiceType(Company company, String name) {

        return serviceTypeDao.findByCompanyAndName(company, name);
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#saveServiceType(busy.schedule.ServiceType)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ServiceType saveServiceType(ServiceType sType) {

        return serviceTypeDao.save(sType);
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#findServicesBetweenDays(org.joda.time.DateTime,
     * org.joda.time.DateTime, busy.role.Role, busy.schedule.ServiceType)
     */
    @Override
    @Transactional(readOnly = true)
    public List<busy.schedule.Service> findServicesBetweenDays(DateTime fromDateTime, DateTime toDateTime, Role role,
        ServiceType serviceType) {

        return serviceDao.findBetweenDays(fromDateTime, toDateTime, role, serviceType);
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleService#saveServices(java.util.List)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveServices(List<busy.schedule.Service> serviceList) {

        for (busy.schedule.Service service : serviceList) {

            serviceDao.save(service);
            saveTimeSlots(service.getTimeSlots(), service.getId());
        }
    }

    /**
     * Saves or updates a list of time slots.
     * 
     * @param timeSlotList
     *            the list of time slots to be saved
     * @param serviceId
     *            the unique id of the service to attach the time slots
     */
    private void saveTimeSlots(List<TimeSlot> timeSlotList, int serviceId) {
        for (TimeSlot timeSlot : timeSlotList) {
            timeSlotDao.save(timeSlot, serviceId);
            saveSchedules(timeSlot.getSchedules(), timeSlot.getId());
        }
    }
    
    /**
     * Saves or updates a list of schedules.
     * 
     * @param scheduleList
     *            the list of schedules to be saved
     * @param timeSlotId
     *            the unique id of the time slot to attach the schedules
     */
    private void saveSchedules(List<Schedule> schedules, int timeSlotId) {
        for (Schedule schedule : schedules) {
            scheduleDao.save(schedule, timeSlotId);
        }
    }

    /* (non-Javadoc)
     * @see busy.schedule.ScheduleService#findServiceTypeById(int)
     */
    @Override
    @Transactional(readOnly = true)
    public ServiceType findServiceTypeById(int id) {
        return serviceTypeDao.findById(id);
    }

}
