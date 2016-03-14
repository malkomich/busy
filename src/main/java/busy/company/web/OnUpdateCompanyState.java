package busy.company.web;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import busy.company.Company;
import busy.user.User;

public class OnUpdateCompanyState extends ApplicationEvent {

	private static final long serialVersionUID = -8633073453998216839L;
	
	private final String appUrl;
	private final Locale locale;
	private final Company company;
	private final User manager;
	
	public OnUpdateCompanyState(Company company, User manager, Locale locale, String appUrl) {
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