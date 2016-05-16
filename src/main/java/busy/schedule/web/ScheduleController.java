package busy.schedule.web;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import busy.company.web.CompanyController;
import busy.role.Role;
import busy.role.RoleService;
import busy.schedule.Schedule;
import busy.schedule.ScheduleService;
import busy.schedule.Service;
import busy.schedule.Service.Repetition;
import busy.user.User;

/**
 * Controller for schedule operations.
 * 
 * @author malkomich
 *
 */
@Controller
@Scope(value = "singleton")
@SessionAttributes(value = {CompanyController.ROLE_SESSION, ScheduleController.SERVICE_FORM_SESSION})
public class ScheduleController {

    /**
     * Spring Model Attributes.
     */
    static final String SERVICE_FORM_SESSION = "serviceForm";

    /**
     * URL Paths.
     */
    private static final String PATH_BOOKINGS_OF_MONTH = "/get_month_bookings";
    private static final String PATH_SERVICES_FORM = "/service_form";
    private static final String PATH_SERVICES_FORM_NEW = "/service_form/new";
    private static final String PATH_SERVICES_FORM_SAVE = "/service_form/save";

    /**
     * JSP's
     */
    private static final String SERVICE_FORM_PAGE = "service-form";

    /**
     * HTTP params.
     */
    private static final String PARAM_DATE_FROM = "from";
    private static final String PARAM_DATE_TO = "to";
    private static final String PARAM_DATE_OFFSET_FROM = "utc_offset_from";
    private static final String PARAM_DATE_OFFSET_TO = "utc_offset_to";

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Request to get all bookings made between the given dates in the specific branch
     * 
     * @param roleIdTmp
     *            the role attached to the requested bookings
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
    public @ResponseBody String getMonthBookings(@RequestParam(value = "role", required = true) String roleIdTmp,
        @RequestParam(value = PARAM_DATE_FROM, required = true) String fromTmp,
        @RequestParam(value = PARAM_DATE_TO, required = true) String toTmp,
        @RequestParam(value = PARAM_DATE_OFFSET_FROM, required = true) String offSetFromTmp,
        @RequestParam(value = PARAM_DATE_OFFSET_TO, required = true) String offSetToTmp, Model model) {

        long from = Long.parseLong(fromTmp);
        long to = Long.parseLong(toTmp);
        Role role = roleService.findRoleById(Integer.parseInt(roleIdTmp));

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

        List<Service> serviceList = scheduleService.findServicesBetweenDays(fromDateTime, toDateTime, role, null);

        JSONObject jsonResult = new JSONObject();
        JSONArray jsonBookings = new JSONArray();

        for (Service service : serviceList) {

            try {
                for (Schedule schedule : service.getSchedules()) {
                    for (User booking : schedule.getBookings()) {
                        JSONObject bookingJSON = new JSONObject();
                        bookingJSON.put("id", "u" + booking.getId() + "s" + service.getId());
                        bookingJSON.put("title", booking.getFirstName() + " " + booking.getLastName());
                        bookingJSON.put("url", "/localhost:8080");
                        bookingJSON.put("class", "event-info");

                        DateTime startTime = service.getStartTime();
                        int minutes = service.getServiceType().getDuration();
                        DateTime endTime = startTime.plus(minutes * 60 * 1000);

                        bookingJSON.put("start", String.valueOf(startTime.getMillis()));
                        bookingJSON.put("end", String.valueOf(endTime.getMillis()));

                        jsonBookings.put(bookingJSON);
                    }
                }
            } catch (JSONException jse) {
                jse.printStackTrace();
            }
        }

        jsonResult.put("result", jsonBookings);
        jsonResult.put("success", 1);

        return jsonResult.toString();
    }

    /**
     * Shows the form to save a service type.
     * 
     * @param dateTmp
     *            the date of the services to modify or create
     * @param model
     *            Spring Model instance
     * @return The JSP view of the dialog form
     */
    @RequestMapping(value = PATH_SERVICES_FORM, method = RequestMethod.GET)
    public String showServiceForm(@RequestParam(value = "date", required = true) String dateTmp, Model model) {

        ServiceListForm form = new ServiceListForm();

        LocalDate date = DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(dateTmp);

        form.setDate(date);

        Role role = (Role) model.asMap().get(CompanyController.ROLE_SESSION);
        List<Role> roles = roleService.findRolesByBranch(role.getBranch());
        form.setExistingRoles(roles);

        form.setExistingRepetitionTypes(Repetition.values());

        List<Service> serviceList = scheduleService.findServicesBetweenDays(date.toDateTime(new LocalTime(0, 0)),
            date.toDateTime(new LocalTime(23, 59)), role, null);

        for (Service service : serviceList) {
            ServiceForm serviceForm = new ServiceForm();
            serviceForm.setId(service.getId());
            serviceForm.setStartTime(service.getStartTime().toLocalTime());
            serviceForm.setServiceType(service.getServiceType());
            serviceForm.setRepeated((service.getCorrelation() > 0) ? true : false);
            for (Schedule schedule : service.getSchedules()) {
                serviceForm.addRole(schedule.getRole());
            }

            form.addService(serviceForm);
        }

        model.addAttribute(SERVICE_FORM_SESSION, form);

        return SERVICE_FORM_PAGE;
    }

    /**
     * Adds a new service item to the services form
     * @param model
     * @return
     */
    @RequestMapping(value = PATH_SERVICES_FORM_NEW, method = RequestMethod.GET)
    public String newService(Model model) {

        ServiceListForm form = (ServiceListForm) model.asMap().get(SERVICE_FORM_SESSION);
        form.addService(new ServiceForm());

        return SERVICE_FORM_PAGE;
    }
}
