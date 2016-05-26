package busy.schedule.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import busy.role.Role;
import busy.schedule.Schedule;
import busy.schedule.Service;
import busy.schedule.Service.Repetition;
import busy.schedule.ServiceType;
import busy.util.SecureSetter;

public class ServiceListForm {

    private LocalDate date;
    private Map<Integer, ServiceType> existingServiceTypes;
    private Map<Integer, Role> existingRoles;
    private List<Repetition> existingRepetitionTypes;

    @Valid
    private List<ServiceForm> services;

    public ServiceListForm() {
        services = new ArrayList<>();
        existingServiceTypes = new HashMap<>();
        existingRoles = new HashMap<>();
        existingRepetitionTypes = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<Integer, ServiceType> getExistingServiceTypes() {
        return existingServiceTypes;
    }

    public void setExistingServiceTypes(List<ServiceType> serviceTypeList) {
        for (ServiceType sType : serviceTypeList) {
            existingServiceTypes.put(sType.getId(), sType);
        }
    }

    public Map<Integer, Role> getExistingRoles() {
        return existingRoles;
    }

    public void setExistingRoles(List<Role> roleList) {
        for (Role role : roleList) {
            existingRoles.put(role.getId(), role);
        }
    }

    public List<Repetition> getExistingRepetitionTypes() {
        return existingRepetitionTypes;
    }

    public void setExistingRepetitionTypes(List<Repetition> repetitionList) {
        existingRepetitionTypes = repetitionList;
    }

    public void setExistingRepetitionTypes(Repetition[] repetitionArray) {
        setExistingRepetitionTypes(Arrays.asList(repetitionArray));
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
    public Map<Integer, List<Service>> toServices() {

        Map<Integer, List<Service>> serviceMap = new HashMap<>();
        int iteration = 0;
        for (ServiceForm form : services) {
            Service service = new Service();
            SecureSetter.setId(service, form.getId());
            LocalTime startTime = DateTimeFormat.forPattern("HH:mm").parseLocalTime(form.getStartTime());
            service.setStartTime(date.toDateTime(startTime));
            service.setServiceType(existingServiceTypes.get(form.getServiceType()));

            for (Integer roleId : form.getRoles()) {
                Schedule schedule = new Schedule();
                schedule.setRole(existingRoles.get(roleId));
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
                    DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(form.getRepetitionDate());
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

            while ((date = date.plusDays(intervalDays)).isBefore(limitMillis)) {

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
