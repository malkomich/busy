package busy.schedule;

import java.io.Serializable;

import busy.role.Role;

public class Service implements Serializable {

    private static final long serialVersionUID = -811217121096779784L;

    private int id;
    private ServiceType serviceType;
    private HourSchedule hourSchedule;
    private Role role;

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public HourSchedule getHourSchedule() {
        return hourSchedule;
    }

    public void setHourSchedule(HourSchedule hourSchedule) {
        this.hourSchedule = hourSchedule;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

}
