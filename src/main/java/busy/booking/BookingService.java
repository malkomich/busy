package busy.booking;

import java.util.List;
import java.util.Map;

import busy.schedule.Service;
import busy.schedule.WeekSchedule;

/**
 * Booking service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface BookingService {

    /**
     * Gets all the bookings made in the requested weeks.
     * 
     * @param weekScheduleList
     *            list of week schedules to find in
     * @return The list of resultant bookings
     */
    Map<Service, List<Booking>> findBookingsByWeekSchedules(List<WeekSchedule> weekScheduleList);

}
