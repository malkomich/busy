package busy.company.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import busy.BusyController;
import busy.company.Branch;
import busy.company.Company;
import busy.company.CompanyService;
import busy.location.Address;
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
public class CompanyController extends BusyController {

    /**
     * Spring Model Attributes.
     */
    static final String REGISTER_COMPANY_REQUEST = "companyForm";
    static final String COUNTRY_ITEMS_REQUEST = "countryItems";
    static final String CATEGORY_ITEMS_REQUEST = "categoryItems";
    static final String COMPANY_REQUEST = "company";
    static final String SERVICE_TYPE_FORM_REQUEST = "serviceTypeForm";
    static final String SERVICE_TYPE_REQUEST = "serviceType";
    static final String BRANCHES_REQUEST = "branches";
    static final String SECTION_REQUEST = "companySection";
    static final String ROLE_FORM_REQUEST = "roleForm";

    /**
     * URL Paths.
     */
    private static final String PATH_ROOT = "/";
    private static final String PATH_REGISTER_COMPANY = "/new_company";
    private static final String PATH_COMPANIES_UPDATE = "/get_company_list";
    private static final String PATH_COMPANY_CHANGE_STATE = "/change_company_state";
    private static final String PATH_COMPANY_SEARCHES = "/get_company_searches";
    private static final String PATH_COMPANY_INFO = "/company/{id}";
    private static final String PATH_SERVICE_TYPE_DELETE = "/service-type/delete";
    private static final String PATH_SERVICE_TYPE_SAVE = "/service-type/save";
    private static final String PATH_RETURN_OBJECT = "/return-model-object";
    private static final String PATH_GET_BRANCHES = "/get_branches";
    private static final String PATH_BRANCH_DATA = "/get_branch_data";
    private static final String PATH_ROLE_SAVE = "/role/save";

    /**
     * JSP's
     */
    private static final String REGISTER_COMPANY_PAGE = "new-company";
    private static final String COMPANY_INFO_PAGE = "company-info";

    private static final String SERVICE_TYPE_FORM_PAGE = "service-type-form";
    private static final String SERVICE_TYPES_FRAGMENT = "service-types";
    private static final String BRANCHES_FRAGMENT = "branches";
    private static final String CALENDAR_FRAGMENT = "calendar";
    private static final String ROLE_FORM_PAGE = "role-form";
    private static final String ROLES_FRAGMENT = "roles";

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

        model.addAttribute(COUNTRY_ITEMS_REQUEST, locationService.findCountries());
        model.addAttribute(CATEGORY_ITEMS_REQUEST, companyService.findCategories());

        return REGISTER_COMPANY_PAGE;
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

        String message = messageSource.getMessage("notification.message.company.pending", null, locale).trim();
        model.addAttribute(MESSAGE_REQUEST, message);

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

        return SERVICE_TYPES_FRAGMENT;
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

    /**
     * [GET] Branches view. It shows a list of branch offices of the company which ID is given in
     * the HTTP request.
     * 
     * @param companyIdTmp
     *            unique ID of the company
     * @param section
     *            identifier of the section which are being requested
     * @param model
     *            Spring model instance
     * @return The page with all branches for the company
     */
    @RequestMapping(value = PATH_GET_BRANCHES, method = RequestMethod.GET)
    public String getBranches(@RequestParam("company_id") String companyIdTmp, @RequestParam("section") String section,
        Model model) {

        int companyId = Integer.parseInt(companyIdTmp);
        List<Branch> branches = companyService.findBranchesByCompanyId(companyId);
        model.addAttribute(BRANCHES_REQUEST, branches);
        model.addAttribute(SECTION_REQUEST, section);

        return BRANCHES_FRAGMENT;
    }

    /**
     * [GET] Branch data view. It shows a different fragment of data from the requested branch
     * office, depending the section which is being requested by the user.
     * 
     * @param branchIdTmp
     *            unique ID of the branch
     * @param section
     *            identifier of the section which are being requested
     * @param model
     *            Spring model instance
     * @return The page with the requested data of the branch office
     */
    @RequestMapping(value = PATH_BRANCH_DATA, method = RequestMethod.GET)
    public String getBranchData(@RequestParam("branch_id") String branchIdTmp, @RequestParam("section") String section,
        Model model) {

        int branchId = Integer.parseInt(branchIdTmp);

        Branch branch = companyService.findBranchById(branchId);

        if (section.equals("booking")) {
            Role role = roleService.findManagerOfBranch(branch);
            model.addAttribute(ROLE_SESSION, role);

            return CALENDAR_FRAGMENT;
        }

        return null;
    }

    /**
     * Shows the form to save a new role.
     * 
     * @param model
     *            Spring Model instance
     * @return The JSP view of the form
     */
    @RequestMapping(value = PATH_ROLE_SAVE, method = RequestMethod.GET)
    public String showRoleForm(Model model) {

        if (!model.containsAttribute(ROLE_FORM_REQUEST)) {
            Role roleForm = new Role();
            Branch branch = ((Role)model.asMap().get(ROLE_SESSION)).getBranch();
            roleForm.setBranch(branch);

            model.addAttribute(ROLE_FORM_REQUEST, roleForm);
        }

        return ROLE_FORM_PAGE;
    }

    /**
     * Request to save a role.
     * 
     * @param roleForm
     *            form with the data of the role
     * @param result
     *            result of the form validation
     * @param model
     *            Spring Model instance
     * @return The operation result
     */
    @RequestMapping(value = PATH_ROLE_SAVE, method = RequestMethod.POST)
    public String saveRoleForm(@ModelAttribute(ROLE_FORM_REQUEST) @Valid Role roleForm, BindingResult result,
        Model model) {

        RoleValidator validator = new RoleValidator();
        validator.validate(roleForm, result);

        if (result.hasErrors()) {
            return ROLE_FORM_PAGE;
        }
        
        // Save the Role here

        return ROLES_FRAGMENT;
    }

}
