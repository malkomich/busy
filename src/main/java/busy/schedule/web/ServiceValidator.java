package busy.schedule.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.schedule.Service.Repetition;

/**
 * New service Form Validator. It validates the consistency of the service data introduced by the
 * user.
 * 
 * @author malkomich
 *
 */
public class ServiceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ServiceListForm serviceListForm = (ServiceListForm) target;

        int index = 0;
        for (ServiceForm serviceForm : serviceListForm.getServices()) {
            Repetition type = serviceForm.getRepetitionType();
            String dateString = serviceForm.getRepetitionDate();
            String regex = "\\d{2}/\\d{2}/\\d{4}";

            if (!Repetition.NONE.equals(type) && (dateString == null || !dateString.matches(regex))) {
                errors.rejectValue("services[" + index + "].repetitionDate", "schedule.service.repetition_date.empty");
            }

            index++;
        }

    }

}
