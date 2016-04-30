package busy.company.web;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import busy.company.Company;
import busy.user.User;

/**
 * Event for a performed update of company status.
 * 
 * @author malkomich
 *
 */
public class OnUpdateCompanyStatus extends ApplicationEvent {

    private static final long serialVersionUID = -8633073453998216839L;

    private String appUrl;
    private Locale locale;
    private Company company;
    private User manager;

    public OnUpdateCompanyStatus(Company company, User manager, Locale locale, String appUrl) {
        super(company);

        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public User getManager() {
        return manager;
    }

}
