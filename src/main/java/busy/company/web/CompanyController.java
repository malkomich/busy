package busy.company.web;

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
import busy.schedule.web.ServiceTypeForm;
import busy.schedule.web.ServiceTypeValidator;
import busy.user.User;
import busy.util.OperationResult;
import busy.util.OperationResult.ResultCode;
import busy.util.SecureSetter;

/**
 * Controller for Company operations, like create or filter by category or name.
 * 
 * @author malkomich
 *
 */
@Controller
@Scope(value = "singleton")
@SessionAttributes(value = {CompanyController.ROLE_SESSION, CompanyController.SERVICE_TYPES_SESSION})
public class CompanyController {

    /**
     * Spring Model Attributes.
     */
    public static final String ROLE_SESSION = "role";
    static final String SERVICE_TYPES_SESSION = "serviceTypes";
    static final String USER_SESSION = "user";

    static final String REGISTER_COMPANY_REQUEST = "companyForm";
    static final String COUNTRY_ITEMS_REQUEST = "countryItems";
    static final String CATEGORY_ITEMS_REQUEST = "categoryItems";
    static final String MESSAGE_REQUEST = "messageFromController";
    static final String COMPANY_REQUEST = "company";
    static final String SERVICE_TYPE_FORM_REQUEST = "serviceTypeForm";
    static final String SERVICE_TYPE_REQUEST = "serviceType";

    /**
     * URL Paths.
     */
    public static final String PATH_SCHEDULE = "/schedule/";
    
    private static final String PATH_ROOT = "/";
    private static final String PATH_REGISTER_COMPANY = "/new_company";
    private static final String PATH_COMPANIES_UPDATE = "/get_company_list";
    private static final String PATH_COMPANY_CHANGE_STATE = "/change_company_state";
    private static final String PATH_COMPANY_SEARCHES = "/get_company_searches";
    private static final String PATH_COMPANY_INFO = "/company/{id}";
    private static final String PATH_SERVICE_TYPE_DELETE = "/service-type/delete";
    private static final String PATH_SERVICE_TYPE_SAVE = "/service-type/save";
    private static final String PATH_RETURN_OBJECT = "/return-model-object";

    private static final String PATH_PARAM_ROLE = "{rId}";
    
    /**
     * JSP's
     */
    private static final String REGISTER_COMPANY_PAGE = "new-company";
    private static final String COMPANY_INFO_PAGE = "company-info";
    private static final String BRANCH_PAGE = "branch";

    private static final String SERVICE_TYPE_FORM_PAGE = "service-type-form";

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
     * @param roleId
     *            unique ID of the role requested
     * @param model
     *            Spring model instance
     * @return The page to register companies
     */
    @RequestMapping(value = PATH_SCHEDULE + PATH_PARAM_ROLE, method = RequestMethod.GET)
    public String showBranchPage(@PathVariable("rId") String roleId, Model model) {

        Role role = roleService.findRoleById(Integer.parseInt(roleId));
        model.addAttribute(ROLE_SESSION, role);

        // Load the service types of the company
        List<ServiceType> serviceTypes = scheduleService.findServiceTypesByCompany(role.getCompany());
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

        String message = messageSource.getMessage("notification.message.company_pending", null, locale).trim();
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

    /**
     * Shows the form to save a service type.
     * 
     * @param id
     *            the unique id of an existing service type
     * @param model
     *            Spring Model instance
     * @return The JSP view of the form
     */
    @RequestMapping(value = PATH_SERVICE_TYPE_SAVE, method = RequestMethod.GET)
    public String showServiceTypeForm(@RequestParam(value = "id", required = false) String id, Model model) {

        if (!model.containsAttribute(SERVICE_TYPE_FORM_REQUEST)) {
            ServiceTypeForm form = new ServiceTypeForm();

            if (id != null) {
                int sTypeId = Integer.parseInt(id);
                ServiceType sType = getServiceTypeFromModel(sTypeId, model);
                form.setId(sTypeId);
                form.setName(sType.getName());
                form.setDescription(sType.getDescription());
                form.setMaxBookingsPerRole(sType.getMaxBookingsPerRole());
                form.setDuration(sType.getDuration());
            }

            model.addAttribute(SERVICE_TYPE_FORM_REQUEST, form);
        }

        return SERVICE_TYPE_FORM_PAGE;
    }

    /**
     * Request to save a service type
     * 
     * @param sTypeForm
     *            form with the data of the service type
     * @param result
     *            result of the form validation
     * @param model
     *            Spring Model instance
     * @return The operation result
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PATH_SERVICE_TYPE_SAVE, method = RequestMethod.POST)
    public String saveServiceType(@ModelAttribute(SERVICE_TYPE_FORM_REQUEST) @Valid ServiceTypeForm sTypeForm,
            BindingResult result, Model model) {

        Company company = ((Role) model.asMap().get(ROLE_SESSION)).getCompany();

        ServiceTypeValidator validator = new ServiceTypeValidator(scheduleService);
        validator.setCompany(company);

        if (!result.hasErrors()) {
            validator.validate(sTypeForm, result);
        }

        if (result.hasErrors()) {
            return showServiceTypeForm(null, model);
        }

        ServiceType sType =
                (sTypeForm.getId() > 0) ? getServiceTypeFromModel(sTypeForm.getId(), model) : new ServiceType();

        sType.setName(sTypeForm.getName());
        sType.setDescription(sTypeForm.getDescription());
        sType.setMaxBookingsPerRole(sTypeForm.getMaxBookingsPerRole());
        sType.setDuration(sTypeForm.getDuration());
        sType.setCompany(company);

        sType = scheduleService.saveServiceType(sType);

        List<ServiceType> list = (List<ServiceType>) model.asMap().get(SERVICE_TYPES_SESSION);
        if (!list.contains(sType)) {
            list.add(sType);
        }

        return "service-types";
    }

    /**
     * Request to delete a service type given its unique identifier
     * 
     * @param idTmp
     *            unique id of the service type to delete
     * @param model
     *            Spring model instance
     * @return The operation result
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PATH_SERVICE_TYPE_DELETE, method = RequestMethod.POST)
    public @ResponseBody OperationResult deleteServiceType(@RequestParam(value = "id", required = true) String idTmp,
            Model model) {

        int id = Integer.parseInt(idTmp);
        ServiceType sType = getServiceTypeFromModel(id, model);

        OperationResult result = scheduleService.deleteServiceType(sType);
        if (ResultCode.OK.equals(result.getCode())) {
            ((List<ServiceType>) model.asMap().get(SERVICE_TYPES_SESSION)).remove(sType);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private ServiceType getServiceTypeFromModel(int id, Model model) {

        Object sTypeList = model.asMap().get(SERVICE_TYPES_SESSION);
        if (sTypeList != null && sTypeList instanceof List) {

            for (ServiceType sType : (List<ServiceType>) sTypeList) {
                if (sType.getId() == id) {
                    return sType;
                }
            }
        }
        return null;
    }

    /**
     * Returns a parsed object from the Spring model as a response to a GET request
     * 
     * @param model
     *            Spring Model instance
     * @param objectKey
     *            the identifier key of the stored object
     * @return The resultant object
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PATH_RETURN_OBJECT, method = RequestMethod.GET)
    public @ResponseBody <T> T returnModelObject(Model model, String objectKey) {
        T object = (T) model.asMap().get(SERVICE_TYPE_REQUEST);
        return object;
    }

}
