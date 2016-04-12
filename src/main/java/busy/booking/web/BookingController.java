package busy.booking.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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
import busy.company.Branch;
import busy.company.CompanyService;
import busy.company.web.CompanyController;
import busy.schedule.ScheduleService;
import busy.schedule.Service;
import busy.schedule.WeekSchedule;
import busy.schedule.YearSchedule;

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
     * Util constants
     */
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 7;

    /**
     * URL Paths.
     */
    private static final String PATH_BOOKINGS_OF_MONTH = "/get_month_bookings";

    /**
     * HTTP params.
     */
    private static final String PARAM_DATE_FROM = "from";
    private static final String PARAM_DATE_TO = "to";
    private static final String PARAM_DATE_OFFSET_FROM = "utc_offset_from";
    private static final String PARAM_DATE_OFFSET_TO = "utc_offset_to";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CompanyService companyService;

    /**
     * Request to get all bookings made between the given dates in the specific branch
     * 
     * @param branchIdTmp
     *            the branch attached to the requested bookings
     * @param fromTmp
     *            the initial instant in milliseconds of the period in which find bookings
     * @param toTmp
     *            the final instant in milliseconds of the period in which find bookings
     * @param offSetMinutesTmp
     *            the offset from UTC in milliseconds of the dates received
     * @param model
     *            Spring model instance
     * @return The list of resultant bookings in JSON format
     */
    @RequestMapping(value = PATH_BOOKINGS_OF_MONTH, method = RequestMethod.GET)
    public @ResponseBody String getMonthBookings(@RequestParam(value = "branch", required = true) String branchIdTmp,
            @RequestParam(value = PARAM_DATE_FROM, required = true) String fromTmp,
            @RequestParam(value = PARAM_DATE_TO, required = true) String toTmp,
            @RequestParam(value = PARAM_DATE_OFFSET_FROM, required = true) String offSetFromTmp,
            @RequestParam(value = PARAM_DATE_OFFSET_TO, required = true) String offSetToTmp, Model model) {

        YearSchedule[] schedule = (YearSchedule[]) model.asMap().get(CompanyController.SCHEDULE_SESSION);

        int branchId = Integer.parseInt(branchIdTmp);
        long from = Long.parseLong(fromTmp);
        long to = Long.parseLong(toTmp);
        
        // Get the inverse of offset due to JS Date implementation
        long offSetFrom = -Long.parseLong(offSetFromTmp);
        long offSetTo = -Long.parseLong(offSetToTmp);

        int offSetFromHours = (int) (offSetFrom / 60);
        int offSetFromMinutes = (int) (offSetFrom % 60);
        int offSetToHours = (int) (offSetTo / 60);
        int offSetToMinutes = (int) (offSetTo % 60);

        DateTimeZone dtZoneFrom = DateTimeZone.forOffsetHoursMinutes(offSetFromHours, offSetFromMinutes);
        DateTimeZone dtZoneTo = DateTimeZone.forOffsetHoursMinutes(offSetToHours, offSetToMinutes);

        DateTime fromDateTime = new DateTime(from, dtZoneFrom);
        // Lower a millisecond to ensure the date are inside the requested month
        DateTime toDateTime = new DateTime(to, dtZoneTo).minus(1);

        int currentYear = fromDateTime.getYear();

        fromDateTime = fromDateTime.withDayOfWeek(FIRST_DAY);
        toDateTime = toDateTime.withDayOfWeek(LAST_DAY);

        boolean weekBetweenYears = fromDateTime.getYear() < currentYear;

        Branch branch = companyService.findBranchById(branchId);

        // Update schedule session variable
        if (schedule[1] != null) {
            if (currentYear < schedule[1].getYear()) {
                schedule[2] = schedule[1];
                schedule[1] = schedule[0];
                schedule[0] = scheduleService.findScheduleByBranch(branch, fromDateTime.getYear());

                model.addAttribute(CompanyController.SCHEDULE_SESSION, schedule);

            } else if (currentYear > schedule[1].getYear()) {
                schedule[0] = schedule[1];
                schedule[1] = schedule[2];
                schedule[2] = scheduleService.findScheduleByBranch(branch, toDateTime.getYear());

                model.addAttribute(CompanyController.SCHEDULE_SESSION, schedule);
            }
        } else {

            schedule[0] = scheduleService.findScheduleByBranch(branch, currentYear - 1);
            schedule[1] = scheduleService.findScheduleByBranch(branch, currentYear);
            schedule[2] = scheduleService.findScheduleByBranch(branch, currentYear + 1);

            model.addAttribute(CompanyController.SCHEDULE_SESSION, schedule);
        }

        List<WeekSchedule> weekSchedules = new ArrayList<WeekSchedule>();
        WeekSchedule weekSchedule = null;

        // Get the week of the first and last days of month
        if (weekBetweenYears) {
            if (schedule[0] != null) {
                weekSchedule = schedule[0].getLastWeekSchedule();
                if (weekSchedule != null) {
                    weekSchedules.add(weekSchedule);
                }
            }
            fromDateTime = fromDateTime.plusWeeks(1);
        }
        int firstWeek = fromDateTime.getWeekOfWeekyear();
        int lastWeek = toDateTime.getWeekOfWeekyear();

        int[] weeks = new int[lastWeek - firstWeek + 1];
        for (int i = firstWeek; i <= lastWeek; i++) {
            weeks[i - firstWeek] = i;
        }

        if (schedule[1] != null) {
            weekSchedules.addAll(schedule[1].getWeekSchedules(weeks));
        }

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

                    int day = service.getHourSchedule().getDaySchedule().getDayOfWeek();
                    int week = service.getHourSchedule().getDaySchedule().getWeekSchedule().getWeekOfYear();
                    int year = service.getHourSchedule().getDaySchedule().getWeekSchedule().getYearSchedule().getYear();

                    LocalTime startTime = service.getHourSchedule().getStartTime();
                    LocalTime endTime = service.getHourSchedule().getEndTime();

                    DateTime iniDate = new DateTime().withYear(year).withWeekOfWeekyear(week).withDayOfWeek(day)
                            .withTime(startTime);
                    DateTime endDate =
                            new DateTime().withYear(year).withWeekOfWeekyear(week).withDayOfWeek(day).withTime(endTime);

                    bookingJSON.put("start", String.valueOf(iniDate.getMillis()));
                    bookingJSON.put("end", String.valueOf(endDate.getMillis()));

                    jsonBookings.put(bookingJSON);
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
