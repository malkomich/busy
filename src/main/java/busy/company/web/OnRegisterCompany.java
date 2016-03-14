package busy.company.web;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import busy.role.Role;

public class OnRegisterCompany extends ApplicationEvent {

	private static final long serialVersionUID = -2640861557857133491L;
	
	private final String appUrl;
	private final Locale locale;
	private final Role manager;
	
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
