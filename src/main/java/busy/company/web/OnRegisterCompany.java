package busy.company.web;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import busy.role.Role;

/**
 * Event for a performed registration of a company.
 * 
 * @author malkomich
 *
 */
public class OnRegisterCompany extends ApplicationEvent {

    private static final long serialVersionUID = -2640861557857133491L;

    private String appUrl;
    private Locale locale;
    private Role manager;

    public OnRegisterCompany(Role manager, Locale locale, String appUrl) {
        super(manager);

        this.manager = manager;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Role getManager() {
        return manager;
    }

}
