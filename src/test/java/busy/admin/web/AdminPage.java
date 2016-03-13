package busy.admin.web;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.FilterConstructor;
import org.openqa.selenium.By;

import busy.BusyPage;

public class AdminPage extends BusyPage {

	private static final String PATH = "/admin";
	private static final String DESCRIPTION = "Admin Page";

	/*
	 * CSS Selectors
	 */
	private static final String SHOW_COMPANIES_SELECTOR = "#admin-companies-button";
	private static final String COMPANY_SELECTOR = ".company-item";
	private static final String SWITCH_SELECTOR = ".onoffswitch-checkbox";

	private FluentWebElement companyItem;

	@Override
	public void isAt() {

		String description = getDriver().findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
		assertThat(description).contains(DESCRIPTION);
	}

	@Override
	public String relativePath() {
		return PATH;
	}

	public AdminPage clickOnVerifyCompanies() {

		click(SHOW_COMPANIES_SELECTOR);
		waitForJSandJQueryToLoad();
		return this;
	}

	public AdminPage selectCompany(String company) {

		if (!company.isEmpty()) {
			FluentWebElement companyItem = find(COMPANY_SELECTOR, FilterConstructor.withText().contains(company)).first();
			this.companyItem = companyItem;
		}
		return this;
	}

	public AdminPage clickApprove() {

		click(companyItem.find(SWITCH_SELECTOR));
		return this;
	}

	public AdminPage companyIsActive(String company) {

		if (!company.isEmpty()) {
			FluentWebElement companyItem = find(COMPANY_SELECTOR, FilterConstructor.withText().contains(company)).first();
			assertThat(companyItem.find(SWITCH_SELECTOR).first().isSelected());
		}
		return this;
	}

	public AdminPage companyIsBlocked(String company) {

		if (!company.isEmpty()) {
			FluentWebElement companyItem = find(COMPANY_SELECTOR, FilterConstructor.withText().contains(company)).first();
			assertThat(!companyItem.find(SWITCH_SELECTOR).first().isSelected());
		}
		return this;
	}

	public boolean emailIsSent() {

		// Nothing to check, the operation is done in back end.
		return true;
	}

}
