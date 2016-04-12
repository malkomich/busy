package busy.schedule;

import java.io.Serializable;

import busy.company.Company;

public class ServiceType implements Serializable {

    private static final long serialVersionUID = -4876036444292584389L;

    private int id;
    private String name;
    private String description;
    private int maxBookingsPerRole;
    private int duration;
    private Company company;

    public ServiceType() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxBookingsPerRole() {
        return maxBookingsPerRole;
    }

    public void setMaxBookingsPerRole(int maxBookingsPerRole) {
        this.maxBookingsPerRole = maxBookingsPerRole;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
