package busy.schedule.web;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LocalTimeFormatter implements Formatter<LocalTime> {

    private DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
    
    @Override
    public String print(LocalTime object, Locale locale) {

        return formatter.print(object);
    }

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {

        return formatter.parseLocalTime(text);
    }

}
