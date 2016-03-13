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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Controller for Company operations, like create or filter by category or name.
 * 
 * @author malkomich
 *
 */
@Controller
public class CompanyController {

	/**
	 * Spring Model Attributes.
	 */
	static final String USER_SESSION = "user";

	static final String REGISTER_COMPANY_REQUEST = "companyForm";

	static final String COUNTRY_ITEMS_REQUEST = "countryItems";
	static final String CATEGORY_ITEMS_REQUEST = "categoryItems";
	static final String MESSAGE_REQUEST = "messageFromController";

	/**
	 * URL Paths.
	 */
	private static final String PATH_ROOT = "/";
	private static final String PATH_REGISTER_COMPANY = "new_company";
	private static final String PATH_COMPANIES_UPDATE = "get_company_list";
	private static final String PATH_COMPANY_CHANGE_STATE = "change_company_state";

	/**
	 * JSP's
	 */
	private static final String REGISTER_COMPANY_PAGE = "new-company";

	@Autowired
	private CompanyService companyService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	/**
	 * Shows the form for create a new Company.
	 * 
	 * @param model
	 * @return
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
		SecureSetter.setAttribute(branch, "setHeadquarter", Boolean.class, true);

		companyService.saveBranch(branch);

		Role role = new Role();
		role.setUser((User) session.getAttribute(USER_SESSION));
		role.setBranch(branch);
		SecureSetter.setAttribute(role, "setManager", Boolean.class, true);
		role.setActivity("Jefe");

		roleService.saveRole(role);

		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + MESSAGE_REQUEST, result);
		String message = messageSource.getMessage("company_pending.message", null, locale);
		redirectAttributes.addFlashAttribute(MESSAGE_REQUEST, message);

		return "redirect:" + PATH_ROOT;
	}

	@RequestMapping(value = PATH_COMPANIES_UPDATE, method = RequestMethod.GET)
	public @ResponseBody List<Company> updateCompanies(ModelMap modelMap) {

		return companyService.findAllCompanies();
	}

	@RequestMapping(value = PATH_COMPANY_CHANGE_STATE, method = RequestMethod.POST)
	public void updateState(@RequestParam(value = "id", required = true) String companyId,
			@RequestParam(value = "active", required = true) boolean active, ModelMap modelMap, WebRequest request) {

		Company company = companyService.findCompanyById(Integer.parseInt(companyId));
		SecureSetter.setAttribute(company, "setActive", Boolean.class, active);
		companyService.saveCompany(company);
		
		Role manager = roleService.findCompanyManager(company);
		eventPublisher.publishEvent(new OnUpdateCompanyState(company, manager.getUser(), request.getLocale(), request.getContextPath()));
	}

}
