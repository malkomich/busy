package busy.schedule;

import java.io.Serializable;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import busy.company.Company;

public class ServiceType implements Serializable {

    private static final long serialVersionUID = -4876036444292584389L;

    private int id;
    private String name;
    private String description;
    private Integer maxBookingsPerRole;
    private Integer duration;
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
        
        if (duration != null && duration > 60*24) {
            throw new IllegalArgumentException("The service duration cannot more than a day.");
        }
        
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getCompanyId() {
        return (company != null) ? company.getId() : null;
    }

    @Override
    public String toString() {
        LocalTime durationTime = new LocalTime(duration / 60, duration % 60);
        String output = DateTimeFormat.forPattern("HH:mm").print(durationTime);
        return name + " [" + output + "]";
    }
}
