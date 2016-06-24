package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.joda.time.LocalTime;

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
        
        public int getType() {
            return type;
        }

        public String getMsgCode() {
            return msgCode;
        }
        
        private int type;
        private String msgCode;

        private Repetition(int type, String msgCode) {
            this.type = type;
            this.msgCode = msgCode;
        }
    }

    private int id;
    @NotNull(message = "{schedule.service.service_type.empty}")
    private ServiceType serviceType;
    private Repetition repetition;

    @Valid
    private List<TimeSlot> timeSlots;

    public Service() {
        timeSlots = new ArrayList<>();
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
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        timeSlots.add(timeSlot);
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceTypeId() {
        return (serviceType != null) ? serviceType.getId() : null;
    }

    public LocalTime getStartTime() {
        return timeSlots.get(0).getStartTime();
    }
    
    public LocalTime getEndTime() {
        
        int size = timeSlots.size();
        LocalTime time = (size > 1) ? timeSlots.get(size - 1).getStartTime() : getStartTime();
        int minutes = serviceType.getDuration();
        
        return time.plusMillis(minutes * 60 * 1000); 
    }

}
