package busy.schedule;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import busy.company.Company;

public class ServiceType implements Serializable {

    private static final long serialVersionUID = -4876036444292584389L;

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Integer maxBookingsPerRole;
    @Expose
    private Integer duration;
    @Expose
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

    public Integer getMaxBookingsPerRole() {
        return maxBookingsPerRole;
    }

    public void setMaxBookingsPerRole(Integer maxBookingsPerRole) {
        if (maxBookingsPerRole != null && maxBookingsPerRole <= 0) {
            throw new IllegalArgumentException("A service type has to let at least 1 booking per role.");
        }
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

    public void setDuration(Integer duration) {
        if (duration != null && duration <= 0) {
            throw new IllegalArgumentException("The service duration must be a positive number of minutes.");
        }
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getCompanyId() {
        return (company != null) ? company.getId() : null;
    }
}
