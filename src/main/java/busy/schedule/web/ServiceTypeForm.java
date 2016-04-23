package busy.schedule.web;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Form model to handle the POST request for service types.
 * 
 * @author malkomich
 *
 */
public class ServiceTypeForm {

    // Fields

    private int id;

    @NotEmpty(message = "{name.required}")
    @Length(max = 20, message = "{name.maxlength}")
    private String name;

    private String description;

    private Integer maxBookingsPerRole;

    private Integer duration;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.maxBookingsPerRole = maxBookingsPerRole;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
