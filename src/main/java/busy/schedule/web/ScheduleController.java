package busy.schedule.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import busy.BusyController;
import busy.company.Company;
import busy.company.web.CompanyController;
import busy.role.Role;
import busy.role.RoleService;
import busy.schedule.ScheduleService;
import busy.schedule.Service;
import busy.schedule.Service.Repetition;
import busy.schedule.ServiceType;

/**
 * Controller for schedule operations.
 *
 * @author malkomich
 *
 */
/**
 * @author malkomich
 *
 */
@Controller
public class ScheduleController extends BusyController {

    /**
     * Spring Model Attributes.
     */
    private static final String SERVICE_FORM_REQUEST = "serviceForm";
    private static final String REPETITION_TYPES_REQUEST = "repetitionTypes";
    private static final String MESSAGE_CODE_REQUEST = "msgCode";

    /**
     * URL Paths.
     */
    private static final String PATH_BOOKINGS_OF_MONTH = "/get_month_bookings";
    private static final String PATH_SERVICES_FORM = "/service_form";
    private static final String PATH_SERVICES_FORM_NEW = "/service_form/new";
    private static final String PATH_SERVICES_FORM_SAVE = "/service_form/save";
    private static final String PATH_SCHEDULE = "/schedule/";

    /**
     * JSP's
     */
    private static final String SERVICE_FORM_PAGE = "service-form";
    private static final String MESSAGE_VIEW = "message";
    private static final String BRANCH_PAGE = "branch";

    /**
     * HTTP params.
     */
    private static final String PARAM_DATE_FROM = "from";
    private static final String PARAM_DATE_TO = "to";
    private static final String PARAM_DATE_OFFSET_FROM = "utc_offset_from";
    private static final String PARAM_DATE_OFFSET_TO = "utc_offset_to";
    private static final String PARAM_ROLE = "roleId";

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute(REPETITION_TYPES_REQUEST)
    public Repetition[] loadRepetitions() {
        return Repetition.values();
    }

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
        JSONArray jsonServices = new JSONArray();

        for (Service service : serviceList) {

            try {
                JSONObject serviceJSON = new JSONObject();

                serviceJSON.put("id", service.getId());

                DateTime startTime = service.getStartTime();

                serviceJSON.put("title", startTime.toString("HH:mm") + " [" + service.getServiceType().getName() + "]");
                serviceJSON.put("url", "#");
                serviceJSON.put("class", "event-info");

                int minutes = service.getServiceType().getDuration();
                // Start time plus minutes converted to millis
                DateTime endTime = startTime.plus(minutes * 60 * 1000);

                serviceJSON.put("start", String.valueOf(startTime.getMillis()));
                serviceJSON.put("end", String.valueOf(endTime.getMillis()));

                jsonServices.put(serviceJSON);

            } catch (JSONException jse) {
                jse.printStackTrace();
            }
        }

        jsonResult.put("result", jsonServices);
        jsonResult.put("success", 1);

        return jsonResult.toString();
    }

    /**
     * Shows the calendar view of a specific branch.
     *
     * @param roleId
     *            unique ID of the role requested
     * @param model
     *            Spring model instance
     * @return The page to register companies
     */
    @RequestMapping(value = PATH_SCHEDULE + "{" + PARAM_ROLE + "}", method = RequestMethod.GET)
    public String showBranchPage(@PathVariable(PARAM_ROLE) String roleId, Model model) {

        Role role = roleService.findRoleById(Integer.parseInt(roleId));
        model.addAttribute(ROLE_SESSION, role);

        updateServiceTypes(role.getCompany(), model);

        return BRANCH_PAGE;
    }

    /**
     * Shows the form to save a service type.
     *
     * @param dateTmp
     *            the date of the services to modify or create
     * @param model
     *            Spring Model instance
     * @param locale
     *            current locale of the browser
     * @return The JSP view of the dialog form
     */
    @RequestMapping(value = PATH_SERVICES_FORM, method = RequestMethod.GET)
    public String showServiceForm(@RequestParam(value = "date", required = true) String dateTmp, Model model) {

        ServiceListForm form = new ServiceListForm();

        Role role = (Role) model.asMap().get(CompanyController.ROLE_SESSION);

        List<ServiceType> serviceTypes = scheduleService.findServiceTypesByCompany(role.getCompany());
        if (serviceTypes.isEmpty()) {
            model.addAttribute(MESSAGE_CODE_REQUEST, "modal.messages.service-type.left");
            return MESSAGE_VIEW;
        }

        updateServiceTypes(role.getCompany(), model);

        Map<Integer, Role> roles = new HashMap<>();
        for (Role roleItem : roleService.findRolesByBranch(role.getBranch())) {
            roles.put(roleItem.getId(), roleItem);
        }
        model.addAttribute(BRANCH_ROLES_SESSION, roles);

        LocalDate date = DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(dateTmp);
        form.setDate(date);

        DateTime fromDate = date.toDateTime(new LocalTime(0, 0));
        DateTime toDate = date.toDateTime(new LocalTime(23, 59));
        List<Service> serviceList = scheduleService.findServicesBetweenDays(fromDate, toDate, role, null);

        for (Service service : serviceList) {
            ServiceForm serviceForm = new ServiceForm(service);
            form.addService(serviceForm);
        }

        if (form.getServices().isEmpty()) {
            form.addService(new ServiceForm());
        }
        model.addAttribute(SERVICE_FORM_REQUEST, form);

        return SERVICE_FORM_PAGE;
    }

    /**
     * Adds a new service item to the services form
     *
     * @param form
     *            form with the services data
     * @param index
     *            index of the service to clone
     * @param result
     *            state of the parsed form
     * @param model
     *            Spring model instance
     * @return The service form dialog view with a new service row
     */
    @RequestMapping(value = PATH_SERVICES_FORM_NEW, method = RequestMethod.POST)
    public String newService(@ModelAttribute(SERVICE_FORM_REQUEST) @Valid ServiceListForm form, BindingResult result,
        Model model) {

        ServiceValidator validator = new ServiceValidator();

        validator.validate(form, result);

        if (result.hasErrors()) {
            return SERVICE_FORM_PAGE;
        }

        form.addService(new ServiceForm());
        model.addAttribute(SERVICE_FORM_REQUEST, form);

        return SERVICE_FORM_PAGE;
    }

    /**
     * Tries to save the services from the input form values, validating them previously.
     * 
     * @param form
     *            form with the services to save
     * @param result
     *            state of the parsed form
     * @param model
     *            Spring model instance
     * @return Redirect to the calendar view, or to the same form on error.
     */
    @RequestMapping(value = PATH_SERVICES_FORM_SAVE, method = RequestMethod.POST)
    public String saveServices(@ModelAttribute(SERVICE_FORM_REQUEST) @Valid ServiceListForm form, BindingResult result,
        Model model) {

        ServiceValidator validator = new ServiceValidator();

        validator.validate(form, result);

        if (result.hasErrors()) {
            return SERVICE_FORM_PAGE;
        }

        @SuppressWarnings("unchecked")
        List<ServiceType> serviceTypes = (List<ServiceType>) model.asMap().get(SERVICE_TYPES_SESSION);
        scheduleService.saveServices(form.toServices(serviceTypes, roleService));

        Role role = (Role) model.asMap().get(CompanyController.ROLE_SESSION);

        return "redirect:" + PATH_SCHEDULE + role.getId();
    }

    /**
     * Load the service types of the company
     *
     * @param company
     *            the company which types will be loaded
     * @param model
     *            Spring model instance
     */
    private void updateServiceTypes(Company company, Model model) {

        List<ServiceType> serviceTypes = scheduleService.findServiceTypesByCompany(company);
        model.addAttribute(SERVICE_TYPES_SESSION, serviceTypes);
    }
}
