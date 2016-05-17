package busy.schedule.web;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import busy.role.Role;
import busy.schedule.Service;
import busy.schedule.Service.Repetition;

public class ServiceListForm {

    private LocalDate date;
    private List<Role> existingRoles;
    private Repetition[] existingRepetitionTypes;
    private List<ServiceForm> services;

    public ServiceListForm() {
        services = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Role> getExistingRoles() {
        return existingRoles;
    }

    public void setExistingRoles(List<Role> roleList) {
        this.existingRoles = roleList;
    }

    public Repetition[] getExistingRepetitionTypes() {
        return existingRepetitionTypes;
    }

    public void setExistingRepetitionTypes(Repetition[] existingRepetitionTypes) {
        this.existingRepetitionTypes = existingRepetitionTypes;
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
        for(ServiceForm form : services) {
            Service service = new Service();
//            service.set 
        }
        
        return serviceList;
    }

}
