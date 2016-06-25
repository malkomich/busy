package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Service model.
 * 
 * @author malkomich
 *
 */
public class Service implements Serializable {

    private static final long serialVersionUID = -811217121096779784L;

    public enum Repetition {
        NONE(0, "schedule.service.repetition.none"),
        DAILY(1, "schedule.service.repetition.daily"),
        WEEKLY(2, "schedule.service.repetition.weekly");

        public static Repetition fromInt(int type) {
            switch (type) {
                case 0:
                    return NONE;
                case 1:
                    return DAILY;
                case 2:
                    return WEEKLY;
                default:
                    return null;
            }
        }

        public Integer getType() {
            return type;
        }

        public String getMsgCode() {
            return msgCode;
        }

        private Integer type;
        private String msgCode;

        private Repetition(int type, String msgCode) {
            this.type = type;
            this.msgCode = msgCode;
        }
    }

    private int id;
    private LocalDate date;

    private ServiceType serviceType;
    private Repetition repetition;

    @Valid
    private List<TimeSlot> timeSlots;

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public Integer getRepetitionType() {
        return (repetition != null) ? repetition.getType() : null;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public void setRepetition(int type) {
        this.repetition = Repetition.fromInt(type);
    }

    public List<TimeSlot> getTimeSlots() {
        if (timeSlots == null) {
            timeSlots = new ArrayList<>();
        }
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        getTimeSlots().add(timeSlot);
    }

    public Integer getServiceTypeId() {
        return (serviceType != null) ? serviceType.getId() : null;
    }

    public DateTime getStartTime() {
        return timeSlots.get(0).getStartDateTime();
    }

    public DateTime getEndTime() {

        int size = timeSlots.size();
        DateTime time = (size > 1) ? timeSlots.get(size - 1).getStartDateTime() : getStartTime();
        int minutes = serviceType.getDuration();

        return time.plusMillis(minutes * 60 * 1000);
    }

    public TimeSlot getLastTimeSlot() {
        return (timeSlots.isEmpty()) ? null : timeSlots.get(timeSlots.size() - 1);
    }

    public void updateTimeSlots() {

        for(TimeSlot timeSlot : timeSlots) {
            timeSlot.setService(this);
        }
    }

}
