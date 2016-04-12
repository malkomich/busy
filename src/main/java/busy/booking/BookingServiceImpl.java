package busy.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import busy.schedule.DaySchedule;
import busy.schedule.HourSchedule;
import busy.schedule.WeekSchedule;

/**
 * Booking service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    public void setBookingDao(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public Map<busy.schedule.Service, List<Booking>> findBookingsByWeekSchedules(List<WeekSchedule> weekScheduleList) {

        List<busy.schedule.Service> serviceList = new ArrayList<busy.schedule.Service>();
        for (WeekSchedule weekSchedule : weekScheduleList) {
            for (DaySchedule daySchedule : weekSchedule.getDayScheduleList()) {
                for (HourSchedule hourSchedule : daySchedule.getHourScheduleList()) {
                    serviceList.addAll(hourSchedule.getServiceList());
                }
            }
        }
        Map<busy.schedule.Service, List<Booking>> map = (serviceList.isEmpty())
                ? new HashMap<busy.schedule.Service, List<Booking>>() : bookingDao.findByServices(serviceList);

        return map;
    }

}
