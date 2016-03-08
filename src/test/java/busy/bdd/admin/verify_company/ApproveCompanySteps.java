package busy.bdd.admin.verify_company;

import org.fluentlenium.core.annotation.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import busy.AbstractFunctionalTest;
import busy.admin.web.AdminPage;
import busy.bdd.company.register.RegisterCompanySteps;
import busy.user.web.LoginPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ApproveCompanySteps extends AbstractFunctionalTest {

	final Logger log = LoggerFactory.getLogger(RegisterCompanySteps.class);

	@Page
	private LoginPage loginPage;
	
	@Page
	private AdminPage adminPage;

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
		
	}
	
	@Given("^I am on the main admin page$")
	public void on_admin_page() throws Throwable {
		
	}
	
	@When("^I click on \"Verify new companies\"$")
	public void click_on_verify_companies() throws Throwable {
		
	}
	
	@Given("^I select one of the pending companies$")
	public void select_company() throws Throwable {
		
	}
	
	@When("^I click on \"Approve company\"$")
	public void click_approve() throws Throwable {
		
	}
	
	@Then("^a verify notification is sent to the manager of the company$")
	public void notification_sent() throws Throwable {
		
	}
	
	@Then("^an email is sent to the manager of the company$")
	public void email_sent() throws Throwable {
		
	}
	
}
