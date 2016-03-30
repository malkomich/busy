package busy.booking.web;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import busy.booking.Booking;
import busy.booking.BookingService;
import busy.company.Branch;
import busy.company.CompanyService;

/**
 * Controller for booking operations.
 * 
 * @author malkomich
 *
 */
@Controller
public class BookingController {

    /**
     * URL Paths.
     */
    private static final String PATH_BOOKINGS_OF_MONTH = "/get_month_bookings";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CompanyService companyService;

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
    public @ResponseBody String getMonthBookings(@RequestParam(value = "branchId", required = true) String branchId,
            @RequestParam(value = "year", required = true) String yearTmp,
            @RequestParam(value = "month", required = true) String monthTmp) {

        Branch branch = companyService.findBranchById(Integer.parseInt(branchId));

        int year = Integer.parseInt(yearTmp);
        int month = Integer.parseInt(monthTmp);

        List<Booking> bookingList = bookingService.findBookingsByBranchAndYearAndMonth(branch, year, month);

        JSONObject jsonResult = new JSONObject();

        try {
            JSONArray jsonBookings = new JSONArray();
            for (Booking booking : bookingList) {
                JSONObject bookingJSON = new JSONObject();
                bookingJSON.put("id", "u" + booking.getUser().getId() + "h" + booking.getHourSchedule().getId());
                bookingJSON.put("title", booking.getUser().getFirstName() + " " + booking.getUser().getLastName());
                bookingJSON.put("url", "/localhost:8080");
                bookingJSON.put("class", "event-info");

                int week = booking.getHourSchedule().getDaySchedule().getWeekSchedule().getWeekOfYear();
                int day = booking.getHourSchedule().getDaySchedule().getDayOfWeek();

                LocalTime startTime = booking.getHourSchedule().getStartTime();
                LocalTime endTime = booking.getHourSchedule().getEndTime();

                DateTime iniDate = new DateTime().withYear(year).withMonthOfYear(month).withWeekOfWeekyear(week)
                        .withDayOfWeek(day).withTime(startTime);
                DateTime endDate = new DateTime().withYear(year).withMonthOfYear(month).withWeekOfWeekyear(week)
                        .withDayOfWeek(day).withTime(endTime);

                bookingJSON.put("start", String.valueOf(iniDate.getMillis()));
                bookingJSON.put("end", String.valueOf(endDate.getMillis()));

                jsonBookings.put(bookingJSON);
            }
            jsonResult.put("result", jsonBookings);
            jsonResult.put("success", 1);
        } catch (JSONException jse) {
            jse.printStackTrace();
        }

        return jsonResult.toString();
    }
}
