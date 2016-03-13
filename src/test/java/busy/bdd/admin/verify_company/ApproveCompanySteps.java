package busy.bdd.admin.verify_company;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.RowMapper;

import busy.AbstractFunctionalTest;
import busy.admin.web.AdminPage;
import busy.user.web.LoginPage;
import busy.util.SQLUtil;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ApproveCompanySteps extends AbstractFunctionalTest {

	private static final String NOTIFICATION_CODE = "notification.message.company_approved";

	@Page
	private LoginPage loginPage;
	
	@Page
	private AdminPage adminPage;
	
	@Autowired
	private MessageSource messageSource;

	@Before
	public void runOnce() {

		String scriptPath = "classpath:database/approve_company-prepare.sql";
		template.execute(getSQLScript(scriptPath));
	}

	@After
	public void rollback() {

		String scriptPath = "classpath:database/approve_company-rollback.sql";
		template.execute(getSQLScript(scriptPath));
	}
	
	@Given("^I am logged as an admin$")
	public void logged_as_admin() throws Throwable {
		
		goTo(loginPage).await().untilPage();
		FluentLeniumAssertions.assertThat(loginPage).isAt();
		
		
		String email = "admin@busy.com";
		String password = "pass";
		
		loginPage.setEmail(email);
		loginPage.setPassword(password);
		loginPage.submit();
	}
	
	@Given("^I am on the main admin page$")
	public void on_admin_page() throws Throwable {
		
		goTo(adminPage).await().untilPage();
		FluentLeniumAssertions.assertThat(adminPage).isAt();
	}
	
	@When("^I click on \"Verify new companies\"$")
	public void click_on_verify_companies() throws Throwable {
		
		adminPage.clickOnVerifyCompanies();
	}
	
	@When("^I select the pending company '(.+)'$")
	public void select_company(String company) throws Throwable {
		
		adminPage.selectCompany(company);
	}
	
	@Then("^I should see the company '(.+)' as blocked or not active$")
	public void company_unblocked(String company) throws Throwable {
		
		adminPage.companyIsBlocked(company);
	}
	
	@When("^I click on \"Approve company\"$")
	public void click_approve() throws Throwable {
		
		adminPage.clickApprove();
		// Waiting for event to be executed 
		Thread.sleep(500);
	}
	
	@Then("^a verify notification is sent to the manager of the company$")
	public void notification_sent() throws Throwable {
		
		String msgResource = messageSource.getMessage(NOTIFICATION_CODE, null, Locale.getDefault());
		
		String scriptPath = "classpath:database/check_notifications.sql";
		String message = template.queryForObject(getSQLScript(scriptPath), new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getString(SQLUtil.MESSAGE);
			}
		});
		System.out.println(message);
		System.out.println(msgResource);
		assertTrue(message.contains(msgResource));
	}
	
	@Then("^an email is sent to the manager of the company$")
	public void email_sent() throws Throwable {
		
		assertTrue(adminPage.emailIsSent());
	}
	
	@Then("^the company '(.+)' is shown as active$")
	public void company_is_active(String company) throws Throwable {
		
		adminPage.companyIsActive(company);
	}
	
}
