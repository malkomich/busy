package busy.company.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.company.Company;
import busy.company.CompanyService;

/**
 * New company Form Validator. It validates if all fields for the new company are valid and checks
 * all the unique keys.
 * 
 * @author malkomich
 *
 */
public class RegisterCompanyValidator implements Validator {

    private CompanyService companyService;

    public RegisterCompanyValidator(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterCompanyValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CompanyForm companyForm = (CompanyForm) target;

        Company company;

        company = companyService.findCompanyByBusinessName(companyForm.getBusinessName());
        if (company != null)
            errors.rejectValue("businessName", "businessname.exists");

        company = companyService.findCompanyByEmail(companyForm.getEmail());
        if (company != null)
            errors.rejectValue("email", "email.exists");

        company = companyService.findCompanyByCif(companyForm.getCif());
        if (company != null)
            errors.rejectValue("cif", "cif.exists");
    }
}
