package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

import busy.service.Service;

/**
 * Hour schedule model. It defines a time schedule in a specific day.
 * 
 * @author malkomich
 *
 */
public class HourSchedule implements Serializable {

    private static final long serialVersionUID = 6866084642788052617L;

    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private DaySchedule daySchedule;
    private List<Service> serviceList;

    public HourSchedule() {
        serviceList = new ArrayList<Service>();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public DaySchedule getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(DaySchedule daySchedule) {
        this.daySchedule = daySchedule;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void addService(Service service) {
        serviceList.add(service);
    }

}
