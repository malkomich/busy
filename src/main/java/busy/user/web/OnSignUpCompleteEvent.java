package busy.user.web;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import busy.user.User;

/**
 * Event for a performed sign up.
 * 
 * @author malkomich
 *
 */
public class OnSignUpCompleteEvent extends ApplicationEvent {

    private static final long serialVersionUID = -2387113760576861285L;

    private final String appUrl;
    private final Locale locale;
    private final User user;

    public OnSignUpCompleteEvent(User user, Locale locale, String appUrl) {
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