package busy.schedule.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
    private Map<Integer, Repetition> existingRepetitionTypes;
    
    @Valid
    private List<ServiceForm> services;

    public ServiceListForm() {
        services = new ArrayList<>();
        existingServiceTypes = new HashMap<>();
        existingRoles = new HashMap<>();
        existingRepetitionTypes = new HashMap<>();
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

    public Map<Integer, Repetition> getExistingRepetitionTypes() {
        return existingRepetitionTypes;
    }

    public void setExistingRepetitionTypes(List<Repetition> repetitionList) {
        for (Repetition repetition : repetitionList) {
            existingRepetitionTypes.put(repetition.getId(), repetition);
        }
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

    public List<Service> toServices() {

        List<Service> serviceList = new ArrayList<>();
        for (ServiceForm form : services) {
            Service service = new Service();
            SecureSetter.setId(service, form.getId());
            LocalTime startTime = DateTimeFormat.forPattern("HH:mm").parseLocalTime(form.getStartTime());
            service.setStartTime(date.toDateTime(startTime));
            service.setServiceType(existingServiceTypes.get(form.getServiceType()));
            
            for(Integer roleId: form.getRoles()) {
                Schedule schedule = new Schedule();
                schedule.setService(service);
                schedule.setRole(existingRoles.get(roleId));
                service.addSchedule(schedule);
            }

            serviceList.add(service);
        }

        return serviceList;
    }

}
