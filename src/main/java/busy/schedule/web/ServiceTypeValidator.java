package busy.schedule.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.company.Company;
import busy.schedule.ScheduleService;
import busy.schedule.ServiceType;

/**
 * Service type Form Validator. It validates if all fields for the service type object.
 * 
 * @author malkomich
 *
 */
public class ServiceTypeValidator implements Validator {

    private ScheduleService scheduleService;

    private Company company;

    public ServiceTypeValidator(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceTypeValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ServiceTypeForm sTypeForm = (ServiceTypeForm) target;

        ServiceType sType = scheduleService.findServiceType(company, sTypeForm.getName());

        if (sType != null && sType.getId() != sTypeForm.getId())
            errors.rejectValue("name", "service-type.name.exists");

    }
}
