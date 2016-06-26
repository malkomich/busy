package busy.schedule.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.LocalDate;

import busy.schedule.Service;

public class ServiceListForm {

    private LocalDate date;

    @Valid
    private List<Service> services;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Service> getServices() {

        updateServices();

        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public Service getLastService() {
        return (services.isEmpty()) ? null : services.get(services.size() - 1);
    }

    private void updateServices() {

        if (services == null) {
            services = new ArrayList<>();
        }

        for (Service service : services) {
            service.setDate(date);
            service.updateTimeSlots();
        }
    }

}
