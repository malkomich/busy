package busy.schedule.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import busy.schedule.ScheduleService;
import busy.schedule.ServiceType;

@Component
public class ServiceTypeFormatter implements Formatter<ServiceType> {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public String print(ServiceType object, Locale locale) {

        return (object != null) ? object.getId().toString() : null;
    }

    @Override
    public ServiceType parse(String text, Locale locale) throws ParseException {

        return scheduleService.findServiceTypeById(Integer.valueOf(text));
    }

}
