package busy.bdd.company.register;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import busy.AbstractFunctionalTest;
import busy.company.web.RegisterCompanyPage;
import busy.user.web.LoginPage;
import busy.user.web.MainPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RegisterCompanySteps extends AbstractFunctionalTest {

	final Logger log = LoggerFactory.getLogger(RegisterCompanySteps.class);

	@Page
	private LoginPage loginPage;
	
	@Page
	private MainPage mainPage;

	@Page
	private RegisterCompanyPage registerCompanyPage;

	@Before
	public void runOnce() {

		String scriptPath = "classpath:database/test/register_company-prepare.sql";
		template.execute(getSQLScript(scriptPath));
	}

	@After
	public void rollback() {

		String scriptPath = "classpath:database/test/register_company-rollback.sql";
		template.execute(getSQLScript(scriptPath));
	}

	@Given("^the user has logged in his account$")
	public void user_has_logged_in() throws Throwable {
		
		goTo(loginPage).await().untilPage();
		FluentLeniumAssertions.assertThat(loginPage).isAt();
		
		
		String email = "user@domain.com";
		String password = "pass";
		
		loginPage.setEmail(email);
		loginPage.setPassword(password);
		loginPage.submit();
	}
	
	@Given("^the user is on main page$")
	public void user_is_on_main_page() throws Throwable {
		
		goTo(mainPage).await().untilPage();
		FluentLeniumAssertions.assertThat(mainPage).isAt();
	}

	@When("^the user clicks on \"Create company\"$")
	public void user_clicks_on_create_company() throws Throwable {
		
		mainPage.clickOnCreateCompany();
	}

	@Then("^the page for register a new company is shown$")
	public void registerCompanyPageIsShown() throws Throwable {
		
		FluentLeniumAssertions.assertThat(registerCompanyPage).isAt();
	}
	
	@When("^the user introduces the trade name \"([^\"]*)\"$")
	public void user_introduces_trade_name(String tradeName) throws Throwable {
		
		registerCompanyPage.setTradeName(tradeName);
	}
	
	@When("^the user introduces the business name \"([^\"]*)\"$")
	public void user_introduces_business_name(String businessName) throws Throwable {
		
		registerCompanyPage.setBusinessName(businessName);
	}
	
	@When("^the user introduces the email \"([^\"]*)\"$")
	public void user_introduces_email(String email) throws Throwable {
		
		registerCompanyPage.setEmail(email);
	}
	
	@When("^the user introduces the cif \"([^\"]*)\"$")
	public void user_introduces_cif(String cif) throws Throwable {
		
		registerCompanyPage.setCif(cif);
	}
	
	@When("^the user selects the country \"([^\"]*)\"$")
	public void user_selects_country(String country) throws Throwable {
		
		registerCompanyPage.selectCountry(country);
	}
	
	@When("^the user selects the city \"([^\"]*)\"$")
	public void user_selects_city(String city) throws Throwable {
		
		registerCompanyPage.selectCity(city);
	}
	
	@When("^the user introduces the zip code \"([^\"]*)\"$")
	public void user_introduces_zipcode(String zipcode) throws Throwable {
		
		registerCompanyPage.setZipcode(zipcode);
	}
	
	@When("^the user introduces the phone number \"([^\"]*)\"$")
	public void user_introduces_phone(String phone) throws Throwable {
		
		registerCompanyPage.setPhone(phone);
	}
	
	@When("^the user introduces the address \"([^\"]*)\"$")
	public void user_introduces_address(String address) throws Throwable {
		
		registerCompanyPage.setAddress(address);
	}
	
	@When("^the user selects the category \"([^\"]*)\"$")
	public void user_selects_category(String category) throws Throwable {
		
		registerCompanyPage.selectCategory(category);
	}
	
	@When("^the user press the Create button$")
	public void user_press_create_button() throws Throwable {
		
		registerCompanyPage.submit();
	}
	
	@Then("^the main page is shown$")
	public void main_page_is_shown() throws Throwable {
		
		FluentLeniumAssertions.assertThat(mainPage).isAt();
		mainPage.await().untilPage();
	}
	
	@Then("^a message informing that the company is pending to approve is shown$")
	public void pending_message_is_shown() throws Throwable {
		
		mainPage.await().untilPage();
		assertTrue(mainPage.companyPendingMessageIsShown());
	}
	
	@When("^the company is approved manually by an admin$")
	public void company_is_approved() throws Throwable {
		
		String scriptPath = "classpath:database/test/company-approve.sql";
		template.execute(getSQLScript(scriptPath));
	}
	
	@Then("^a confirm notification is shown$")
	public void confirm_notification_is_shown() throws Throwable {
		
		goTo(mainPage).await().untilPage();
		assertTrue(mainPage.companyApprovedNotificationIsShown());
	}
	
	@Then("^a business section is shown in my main page$")
	public void business_section_is_shown() throws Throwable {
		
		assertTrue(mainPage.businessSectionIsShown());
	}
	
	@Then("^an error message in the page for register a new company is shown$")
	public void error_message_is_shown() throws Throwable {
		
		assertTrue(registerCompanyPage.errorIsShown());
	}
	
	@When("^the company is rejected manually by an admin$")
	public void company_is_rejected() throws Throwable {
		
		String scriptPath = "classpath:database/test/company-reject.sql";
		template.execute(getSQLScript(scriptPath));
	}
	
	@Then("^a notification with the problem is shown in my account$")
	public void problem_notification_is_shown() throws Throwable {
		
		goTo(mainPage).await().untilPage();
		assertTrue(mainPage.companyRejectedNotificationIsShown());
	}
	
}
