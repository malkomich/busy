package busy.schedule.web;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DateTimeFormatter implements Formatter<DateTime> {

    private org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");

    @Override
    public String print(DateTime object, Locale locale) {

        return formatter.print(object);
    }

    @Override
    public DateTime parse(String text, Locale locale) throws ParseException {

        return formatter.parseDateTime(text);
    }

}
