package busy.company.web;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import busy.company.Branch;
import busy.company.Category;
import busy.company.Company;
import busy.company.CompanyService;
import busy.location.Address;
import busy.location.Country;
import busy.location.LocationService;
import busy.role.Role;
import busy.role.RoleService;
import busy.schedule.ScheduleService;
import busy.schedule.ServiceType;
import busy.schedule.YearSchedule;
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Controller for Company operations, like create or filter by category or name.
 * 
 * @author malkomich
 *
 */
@Controller
@Scope(value="singleton")
@SessionAttributes(value = {CompanyController.SCHEDULE_SESSION})
public class CompanyController {

    /**
     * Spring Model Attributes.
     */
    static final String USER_SESSION = "user";
    public static final String SCHEDULE_SESSION = "schedule";
    static final String SERVICE_TYPES_SESSION = "serviceTypes";

    static final String REGISTER_COMPANY_REQUEST = "companyForm";
    static final String COUNTRY_ITEMS_REQUEST = "countryItems";
    static final String CATEGORY_ITEMS_REQUEST = "categoryItems";
    static final String MESSAGE_REQUEST = "messageFromController";
    static final String COMPANY_REQUEST = "company";
    static final String BRANCH_REQUEST = "branch";

    /**
     * URL Paths.
     */
    private static final String PATH_ROOT = "/";
    private static final String PATH_REGISTER_COMPANY = "/new_company";
    private static final String PATH_COMPANIES_UPDATE = "/get_company_list";
    private static final String PATH_COMPANY_CHANGE_STATE = "/change_company_state";
    private static final String PATH_COMPANY_SEARCHES = "/get_company_searches";
    private static final String PATH_COMPANY_INFO = "/company/{id}";
    private static final String PATH_BRANCH = "/company/{cId}/branch/{bId}";

    /**
     * JSP's
     */
    private static final String REGISTER_COMPANY_PAGE = "new-company";
    private static final String COMPANY_INFO_PAGE = "company-info";
    private static final String BRANCH_PAGE = "branch";

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Shows the form for create a new Company.
     * 
     * @param model
     *            Spring model instance
     * @return The page to register companies
     */
    @RequestMapping(value = PATH_REGISTER_COMPANY, method = RequestMethod.GET)
    public String showRegisterCompany(Model model) {

        if (!model.containsAttribute(REGISTER_COMPANY_REQUEST))
            model.addAttribute(REGISTER_COMPANY_REQUEST, new CompanyForm());

        // Add input selection items to the model.
        Map<String, String> countryItems = new LinkedHashMap<String, String>();
        for (Country country : locationService.findCountries()) {
            countryItems.put(country.getCode(), country.getName());
        }
        model.addAttribute(COUNTRY_ITEMS_REQUEST, countryItems);

        // Add input selection items to the model.
        Map<Integer, String> categoryItems = new LinkedHashMap<Integer, String>();
        for (Category category : companyService.findCategories()) {
            categoryItems.put(category.getId(), category.getName());
        }
        model.addAttribute(CATEGORY_ITEMS_REQUEST, categoryItems);

        return REGISTER_COMPANY_PAGE;
    }

    /**
     * Shows the calendar view of a specific branch.
     * 
     * @param branchId
     *            unique ID of the branch requested
     * @param model
     *            Spring model instance
     * @return The page to register companies
     */
    @RequestMapping(value = PATH_BRANCH, method = RequestMethod.GET)
    public String showBranchPage(@PathVariable("bId") String branchId, Model model) {

        Branch branch = companyService.findBranchById(Integer.parseInt(branchId));
        model.addAttribute(BRANCH_REQUEST, branch);

        int year = Calendar.getInstance().get(Calendar.YEAR);

        // Load 3 year to handle right the weeks between years.
        YearSchedule[] schedule = {scheduleService.findScheduleByBranch(branch, year - 1),
            scheduleService.findScheduleByBranch(branch, year),
            scheduleService.findScheduleByBranch(branch, year + 1)};
        model.addAttribute(SCHEDULE_SESSION, schedule);
        
        // Load the service types of the company
        List<ServiceType> serviceTypes = scheduleService.findServiceTypesByCompany(branch.getCompany());
        model.addAttribute(SERVICE_TYPES_SESSION, serviceTypes);

        return BRANCH_PAGE;
    }

    /**
     * Shows the info page of a company requested by the user.
     * 
     * @param id
     *            unique ID of the company requested
     * @param model
     *            Spring model instance
     * @return The company info page
     */
    @RequestMapping(value = PATH_COMPANY_INFO, method = RequestMethod.GET)
    public String showCompanyInfo(@PathVariable("id") String id, Model model) {

        Company company = companyService.findCompanyById(Integer.parseInt(id));
        model.addAttribute(COMPANY_REQUEST, company);

        return COMPANY_INFO_PAGE;
    }

    /**
     * New register company request, which will try to create a new company.
     * 
     * @param form
     *            form HTTP input request
     * @param result
     *            result form result status
     * @param redirectAttributes
     *            model attributes storage to be propagated in a redirection
     * @param request
     *            Web request interface
     * @param model
     *            Spring model instance
     * @param locale
     *            current locale of the browser
     * @param session
     *            HTTP plain old session instance
     * @return The redirect to root path
     */
    @RequestMapping(value = PATH_REGISTER_COMPANY, method = RequestMethod.POST)
    public String registerCompany(@ModelAttribute(REGISTER_COMPANY_REQUEST) @Valid CompanyForm form,
            BindingResult result, RedirectAttributes redirectAttributes, WebRequest request, Model model, Locale locale,
            HttpSession session) {

        RegisterCompanyValidator validator = new RegisterCompanyValidator(companyService);

        validator.validate(form, result);

        if (result.hasErrors()) {
            return showRegisterCompany(model);
        }

        // Save the new Company data.
        Address address = new Address();
        address.setZipCode(form.getZipCode());
        address.setCity(locationService.findCityById(Integer.parseInt(form.getCityId())));
        address.setAddress1(form.getAddress1());
        address.setAddress2(form.getAddress2());

        locationService.saveAddress(address);

        Company company = new Company();
        company.setTradeName(form.getTradeName());
        company.setBusinessName(form.getBusinessName());
        company.setEmail(form.getEmail());
        company.setCif(form.getCif());

        if (form.getCategoryId() != null)
            company.setCategory(companyService.findCategoryById(Integer.parseInt(form.getCategoryId())));

        companyService.saveCompany(company);

        Branch branch = new Branch();
        branch.setCompany(company);
        branch.setAddress(address);
        branch.setPhone(form.getPhone());
        SecureSetter.setAttribute(branch, "setHeadquarters", Boolean.class, true);

        companyService.saveBranch(branch);

        Role role = new Role();
        role.setUser((User) session.getAttribute(USER_SESSION));
        role.setBranch(branch);
        SecureSetter.setAttribute(role, "setManager", Boolean.class, true);

        roleService.saveRole(role);

        eventPublisher.publishEvent(new OnRegisterCompany(role, request.getLocale(), request.getContextPath()));

        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + MESSAGE_REQUEST, result);
        String message = messageSource.getMessage("notification.message.company_pending", null, locale);
        redirectAttributes.addFlashAttribute(MESSAGE_REQUEST, message);

        return "redirect:" + PATH_ROOT;
    }

    /**
     * Request to find all the current companies available.
     * 
     * @return The list of resultant companies
     */
    @RequestMapping(value = PATH_COMPANIES_UPDATE, method = RequestMethod.GET)
    public @ResponseBody List<Company> updateCompanies() {

        return companyService.findAllCompanies();
    }

    /**
     * Request to change the active status of a company.
     * 
     * @param companyId
     *            unique ID of the company
     * @param active
     *            active status
     * @param request
     *            Web request interface
     */
    @RequestMapping(value = PATH_COMPANY_CHANGE_STATE, method = RequestMethod.POST)
    public void updateState(@RequestParam(value = "id", required = true) String companyId,
            @RequestParam(value = "active", required = true) boolean active, WebRequest request) {

        Company company = companyService.findCompanyById(Integer.parseInt(companyId));
        SecureSetter.setAttribute(company, "setActive", Boolean.class, active);
        companyService.saveCompany(company);

        Role manager = roleService.findCompanyManager(company);
        eventPublisher.publishEvent(
                new OnUpdateCompanyStatus(company, manager.getUser(), request.getLocale(), request.getContextPath()));
    }

    /**
     * Request to find the list of companies which contains a given portion of his name.
     * 
     * @param partialName
     *            pattern of company name to find
     * @return The list of resultant companies
     */
    @RequestMapping(value = PATH_COMPANY_SEARCHES, method = RequestMethod.GET)
    public @ResponseBody List<Company>
            getCompanySearches(@RequestParam(value = "partialName", required = true) String partialName) {

        return companyService.findActiveCompaniesByPartialName(partialName);
    }

}
