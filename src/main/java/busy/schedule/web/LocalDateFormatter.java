package busy.schedule.web;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateFormatter implements Formatter<LocalDate> {

    private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
    
    @Override
    public String print(LocalDate object, Locale locale) {

        return formatter.print(object);
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {

        return formatter.parseLocalDate(text);
    }

}
