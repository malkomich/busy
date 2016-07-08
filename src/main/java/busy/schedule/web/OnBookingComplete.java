package busy.schedule.web;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import busy.user.User;

/**
 * Event for a performed booking of a scheduled time slot.
 * 
 * @author malkomich
 *
 */
public class OnBookingComplete extends ApplicationEvent {


    private static final long serialVersionUID = 2725808275387573672L;
    
    private String appUrl;
    private Locale locale;
    private User user;

    public OnBookingComplete(User user, Locale locale, String appUrl) {

        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }

}
