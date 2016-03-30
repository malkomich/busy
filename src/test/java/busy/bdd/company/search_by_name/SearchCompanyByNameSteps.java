package busy.bdd.company.search_by_name;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.annotation.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import busy.AbstractFunctionalTest;
import busy.company.web.CompanyPage;
import busy.user.web.LoginPage;
import busy.user.web.MainPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchCompanyByNameSteps extends AbstractFunctionalTest {

    final Logger log = LoggerFactory.getLogger(SearchCompanyByNameSteps.class);

    @Page
    private LoginPage loginPage;

    @Page
    private MainPage mainPage;

    @Page
    private CompanyPage companyInfoPage;

    @Before
    public void runOnce() {

        String scriptPath = "classpath:database/search_company_by_name-prepare.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @After
    public void rollback() {

        String scriptPath = "classpath:database/search_company_by_name-rollback.sql";
        template.execute(getSQLScript(scriptPath));
    }

    @Given("^I am logged as an user$")
    public void logged_as_user() throws Throwable {

        goTo(loginPage).await().untilPage();
        FluentLeniumAssertions.assertThat(loginPage).isAt();

        String email = "user@domain.com";
        String password = "pass";

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.submit();
    }

    @Given("^I am on the main page$")
    public void on_main_page() throws Throwable {

        goTo(mainPage).await().untilPage();
        FluentLeniumAssertions.assertThat(mainPage).isAt();
    }

    @When("^I click on the search bar$")
    public void click_on_search_bar() throws Throwable {

        mainPage.clickOnSearchBar();
    }

    @When("^I write the name of company '(.+)'$")
    public void write_company_name(String companyName) throws Throwable {

        mainPage.fillSearchBar(companyName);
    }

    @Then("^I should see a list where '(.+)' is shown$")
    public void see_list_populated(String companyName) throws Throwable {

        assertTrue(mainPage.isCompanyInSearchList(companyName));
    }

    @When("^I click on the company '(.+)'$")
    public void click_company(String companyName) throws Throwable {

        mainPage.selectCompanyInSearchList(companyName);
    }

    @Then("^I should see the company information page$")
    public void company_information_page_shown() throws Throwable {

        companyInfoPage.isAt();
    }

    @Then("^I should not see any list, or an empty list of companies$")
    public void see_list_none_or_empty() throws Throwable {

        assertFalse(mainPage.isSearchListShown());
    }

}
