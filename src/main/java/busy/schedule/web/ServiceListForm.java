package busy.schedule.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import busy.role.Role;
import busy.role.RoleService;
import busy.schedule.Schedule;
import busy.schedule.Service;
import busy.schedule.ServiceType;
import busy.util.SecureSetter;

public class ServiceListForm {

    private LocalDate date;

    @Valid
    private List<ServiceForm> services = new ArrayList<>();

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ServiceForm> getServices() {
        return services;
    }

    public void setServices(List<ServiceForm> services) {
        this.services = services;
    }

    public void addService(ServiceForm service) {
        services.add(service);
    }

    /**
     * Parse the form data to a Map of Service object lists grouped by correlation. The repeated
     * services are correlated.
     * 
     * @return
     */
    public Map<Integer, List<Service>> toServices(List<ServiceType> sTypes, RoleService roleService) {

        Map<Integer, List<Service>> serviceMap = new HashMap<>();
        int iteration = 0;
        for (ServiceForm form : services) {
            Service service = new Service();
            SecureSetter.setId(service, form.getId());
            LocalTime startTime = DateTimeFormat.forPattern("HH:mm").parseLocalTime(form.getStartTime());
            service.setStartTime(date.toDateTime(startTime));

            for (ServiceType sType : sTypes) {
                if (sType.getId() == form.getServiceType()) {
                    service.setServiceType(sType);
                }
            }

            List<Role> roleList =
                roleService.findRolesById(form.getRoles().toArray(new Integer[form.getRoles().size()]));
            for (Role role : roleList) {
                Schedule schedule = new Schedule();
                schedule.setRole(role);
                service.addSchedule(schedule);
            }

            List<Service> serviceList = new ArrayList<>();
            serviceList.add(service);
            serviceMap.put(iteration, serviceList);

            int intervalDays;
            switch (form.getRepetitionType()) {
                case DAILY:
                    intervalDays = 1;
                    break;
                case WEEKLY:
                    intervalDays = 7;
                    break;
                default:
                    intervalDays = 0;
                    break;
            }

            // Only if the user has requested the repetition
            if (intervalDays > 0) {

                LocalDate repetitionDate =
                    DateTimeFormat.forPattern("MM/dd/yyyy").parseLocalDate(form.getRepetitionDate());
                List<Service> repeatedServices = getRepeatedServices(service, intervalDays, repetitionDate);

                for (Service s : repeatedServices) {
                    serviceMap.get(iteration).add(s);
                }
            }

            iteration++;
        }

        return serviceMap;
    }

    /**
     * Gets a list of services with the amount of service repetitions requested till the given date,
     * with an interval of days between each repetition.
     * 
     * @param service
     *            the service to be repeated
     * @param intervalDays
     *            interval of days between each repetition
     * @param repetitionDate
     *            date till the service will be repeated
     * @return The list of resultant repeated services
     */
    private List<Service> getRepeatedServices(Service service, int intervalDays, LocalDate repetitionDate) {

        List<Service> serviceList = new ArrayList<>();

        if (intervalDays > 0) {
            DateTime date = service.getStartTime();
            long limitMillis = repetitionDate.toDateTime(date.toLocalTime()).getMillis();

            while (!(date = date.plusDays(intervalDays)).isAfter(limitMillis)) {

                Service serviceCopy = new Service();
                serviceCopy.setStartTime(date);
                serviceCopy.setServiceType(service.getServiceType());
                serviceCopy.setSchedules(service.getScheduleReplications());

                serviceList.add(serviceCopy);
            }

        }
        return serviceList;
    }

}
