package busy.schedule.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import busy.schedule.Schedule;
import busy.schedule.Service;
import busy.schedule.Service.Repetition;

public class ServiceForm {

    // Fields

    private int id;

    @NotEmpty(message = "{schedule.service.start_time.empty}")
    private String startTime;

    @NotNull(message = "{schedule.service.service_type.empty}")
    private Integer serviceType;

    @NotEmpty(message = "{schedule.service.roles.empty}")
    private List<Integer> roles;

    private Repetition repetitionType;
    private String repetitionDate;
    private boolean repeated;

    public ServiceForm(Service service) {
        this();
        id = service.getId();
        startTime = service.getStartTime().toString("HH:mm");
        serviceType = service.getServiceType().getId();
        repeated = (service.getCorrelation() > 0) ? true : false;
        for (Schedule schedule : service.getSchedules()) {
            if (!roles.contains(schedule.getRole())) {
                roles.add(schedule.getRoleId());
            }
        }
    }

    public ServiceForm() {
        roles = new ArrayList<>();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public void addRole(Integer role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public Repetition getRepetitionType() {
        return repetitionType;
    }

    public void setRepetitionType(Repetition repetitionType) {
        this.repetitionType = repetitionType;
    }

    public String getRepetitionDate() {
        return repetitionDate;
    }

    public void setRepetitionDate(String repetitionDate) {
        this.repetitionDate = repetitionDate;
    }

}
