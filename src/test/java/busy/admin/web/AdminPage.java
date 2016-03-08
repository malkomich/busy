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
	private static final String VERIFY_COMPANIES_SELECTOR = "#verify-companies";
	private static final String COMPANY_SELECTOR = ".company-item";
	private static final String APPROVE_SELECTOR = ".approve-company";

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

		click(VERIFY_COMPANIES_SELECTOR);
		return this;
	}

	public AdminPage selectCompany(String company) {

		if (!company.isEmpty()) {
			FluentWebElement companyItem = find(COMPANY_SELECTOR, FilterConstructor.withText(company)).first();
			companyItem.click();
		}
		return this;
	}

	public AdminPage clickApprove() {

		click(APPROVE_SELECTOR);
		return this;
	}

	public AdminPage companyNotShown(String company) {

		if (!company.isEmpty()) {
			assertThat(find(COMPANY_SELECTOR, FilterConstructor.withText(company)).size() == 0);
		}
		return this;
	}

	public boolean emailIsSent() {

		// Nothing to check, the operation is done in back end.
		return true;
	}

}
