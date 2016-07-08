package busy.schedule.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import busy.schedule.Service.Repetition;

@Component
public class RepetitionFormatter implements Formatter<Repetition> {

    @Override
    public String print(Repetition object, Locale locale) {

        return object.getType().toString();
    }

    @Override
    public Repetition parse(String text, Locale locale) throws ParseException {

        return Repetition.fromInt(Integer.parseInt(text));
    }

}
