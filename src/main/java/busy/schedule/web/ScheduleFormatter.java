package busy.schedule.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import busy.schedule.Schedule;
import busy.schedule.ScheduleService;

@Component
public class ScheduleFormatter implements Formatter<Schedule> {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public String print(Schedule object, Locale locale) {

        return (object != null) ? String.valueOf(object.getId()) : null;
    }

    @Override
    public Schedule parse(String text, Locale locale) throws ParseException {

        return scheduleService.findScheduleById(Integer.valueOf(text));
    }

}
