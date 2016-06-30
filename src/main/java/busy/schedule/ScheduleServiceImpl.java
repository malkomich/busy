package busy.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import busy.company.Company;
import busy.role.Role;
import busy.schedule.Service.Repetition;
import busy.user.User;
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
    
    @Autowired
    private BookingDao bookingDao;

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

    /* (non-Javadoc)
     * @see busy.schedule.ScheduleService#findServicesByDay(org.joda.time.LocalDate, busy.role.Role, busy.schedule.ServiceType)
     */
    @Override
    public List<busy.schedule.Service> findServicesByDay(LocalDate date, Role role, ServiceType serviceType) {

        DateTime fromDate = date.toDateTime(new LocalTime(0, 0));
        DateTime toDate = date.toDateTime(new LocalTime(23, 59));
        
        List<busy.schedule.Service> services = findServicesBetweenDays(fromDate, toDate, role, serviceType);
        List<busy.schedule.Service> servicesToRemove = new ArrayList<>();
        for(busy.schedule.Service service : services) {
            if(Repetition.WEEKLY.equals(service.getRepetition()) && service.getStartTime().getDayOfWeek() != date.getDayOfWeek()) {
                servicesToRemove.add(service);
            }
        }
        services.removeAll(servicesToRemove);
        return services;
    }

    /* (non-Javadoc)
     * @see busy.schedule.ScheduleService#findTimeSlotById(int)
     */
    @Override
    public TimeSlot findTimeSlotById(int id) {

        return timeSlotDao.findById(id);
    }

    /* (non-Javadoc)
     * @see busy.schedule.ScheduleService#findScheduleById(java.lang.Integer)
     */
    @Override
    public Schedule findScheduleById(Integer id) {

        return scheduleDao.findById(id);
    }

    /* (non-Javadoc)
     * @see busy.schedule.ScheduleService#saveBooking(busy.user.User, busy.schedule.Schedule)
     */
    @Override
    public void saveBooking(User user, Schedule schedule) {

        bookingDao.save(user, schedule);
    }

}
