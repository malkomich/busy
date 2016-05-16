package busy.schedule.web;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import busy.role.Role;
import busy.schedule.ServiceType;
import busy.schedule.Service.Repetition;

public class ServiceForm {

    // Fields

    private int id;
    private String startTime;
    private ServiceType serviceType;
    private List<Role> roles;
    private Repetition repetitionType;
    private LocalDate repetitionDate;
    private boolean repeated;

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
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = DateTimeFormat.forPattern("HH:mm").print(startTime);
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
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

    public LocalDate getRepetitionDate() {
        return repetitionDate;
    }

    public void setRepetitionDate(LocalDate repetitionDate) {
        this.repetitionDate = repetitionDate;
    }

}
