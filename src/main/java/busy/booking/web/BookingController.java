package busy.booking.web;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import busy.booking.Booking;
import busy.booking.BookingService;
import busy.company.web.CompanyController;
import busy.schedule.WeekSchedule;
import busy.schedule.YearSchedule;
import busy.service.Service;

/**
 * Controller for booking operations.
 * 
 * @author malkomich
 *
 */
@Controller
@SessionAttributes(value = {CompanyController.SCHEDULE_SESSION})
public class BookingController {

    /**
     * URL Paths.
     */
    private static final String PATH_BOOKINGS_OF_MONTH = "/get_month_bookings";

    @Autowired
    private BookingService bookingService;

    /**
     * Request to get all bookings made in the specific month of the given branch.
     * 
     * @param branchId
     *            the branch attached to the requested bookings
     * @param year
     *            the year which the month belong to
     * @param month
     *            the month which bookings are requested
     * @return The list of resultant bookings in JSON format
     */
    @RequestMapping(value = PATH_BOOKINGS_OF_MONTH, method = RequestMethod.GET)
    public @ResponseBody String getMonthBookings(@RequestParam(value = "month", required = true) String monthTmp,
            Model model) {

        YearSchedule yearSchedule = (YearSchedule) model.asMap().get(CompanyController.SCHEDULE_SESSION);

        int month = Integer.parseInt(monthTmp);
        int year = yearSchedule.getYear();

        // Joda Datetime count the weeks of year from the first week with at least 4 days
        boolean extraWeek = new DateTime().withYear(year).withDayOfYear(1).getWeekyear() != year;

        // Get the week of the first day of month
        DateTime dateTime = new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(1);
        int firstWeek = extraWeek ? dateTime.plusWeeks(1).getWeekOfWeekyear() : dateTime.getWeekOfWeekyear();

        // Get the week of the last day of month
        dateTime = dateTime.plusMonths(1).minusDays(1);
        int lastWeek = extraWeek ? dateTime.getWeekOfWeekyear() + 1 : dateTime.getWeekOfWeekyear();

        int[] weeks = new int[lastWeek - firstWeek + 1];
        for (int i = firstWeek; i <= lastWeek; i++) {
            weeks[i - firstWeek] = i;
        }

        List<WeekSchedule> weekSchedules = yearSchedule.getWeekSchedules(weeks);

        Map<Service, List<Booking>> bookingMap = bookingService.findBookingsByWeekSchedules(weekSchedules);

        JSONObject jsonResult = new JSONObject();
        JSONArray jsonBookings = new JSONArray();

        for (Service service : bookingMap.keySet()) {
            List<Booking> bookingList = bookingMap.get(service);

            try {
                for (Booking booking : bookingList) {
                    JSONObject bookingJSON = new JSONObject();
                    bookingJSON.put("id", "u" + booking.getUser().getId() + "h" + service.getHourSchedule().getId());
                    bookingJSON.put("title", booking.getUser().getFirstName() + " " + booking.getUser().getLastName());
                    bookingJSON.put("url", "/localhost:8080");
                    bookingJSON.put("class", "event-info");

                    int week = service.getHourSchedule().getDaySchedule().getWeekSchedule().getWeekOfYear();
                    int day = service.getHourSchedule().getDaySchedule().getDayOfWeek();

                    LocalTime startTime = service.getHourSchedule().getStartTime();
                    LocalTime endTime = service.getHourSchedule().getEndTime();

                    DateTime iniDate = new DateTime().withYear(year).withMonthOfYear(month).withWeekOfWeekyear(week)
                            .withDayOfWeek(day).withTime(startTime);
                    DateTime endDate = new DateTime().withYear(year).withMonthOfYear(month).withWeekOfWeekyear(week)
                            .withDayOfWeek(day).withTime(endTime);

                    bookingJSON.put("start", String.valueOf(iniDate.getMillis()));
                    bookingJSON.put("end", String.valueOf(endDate.getMillis()));

                    jsonBookings.put(bookingJSON);
                    System.out.println(bookingJSON);
                }
            } catch (JSONException jse) {
                jse.printStackTrace();
            }
        }

        jsonResult.put("result", jsonBookings);
        jsonResult.put("success", 1);

        return jsonResult.toString();
    }
}
