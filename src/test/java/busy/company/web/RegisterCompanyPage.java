package busy.company.web;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import busy.BusyPage;

/**
 * Page for the creation of a new company.
 * 
 * @author malkomich
 *
 */
public class RegisterCompanyPage extends BusyPage {

    private static final String PATH = "/new_company";
    private static final String DESCRIPTION = "New company Page";

    /*
     * CSS Selectors
     */
    private static final String TRADENAME_SELECTOR = "#tradeName";
    private static final String BUSINESSNAME_SELECTOR = "#businessName";
    private static final String EMAIL_SELECTOR = "#email";
    private static final String CIF_SELECTOR = "#cif";
    private static final String COUNTRY_SELECTOR = "#country";
    private static final String CITY_SELECTOR = "#city";
    private static final String ZIPCODE_SELECTOR = "#zipcode";
    private static final String PHONE_SELECTOR = "#phone";
    private static final String ADDRESS1_SELECTOR = "#address1";
    private static final String CATEGORY_SELECTOR = "#category";

    private static final String SUBMIT_SELECTOR = "#submit";
    private static final String ERROR_SELECTOR = "span.error";

    @Override
    public String relativePath() {
        return PATH;
    }

    @Override
    public void isAt() {

        String description = getDriver().findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertThat(description).contains(DESCRIPTION);
    }

    public RegisterCompanyPage setTradeName(String tradeName) {

        fill(TRADENAME_SELECTOR).with(tradeName);
        return this;
    }

    public RegisterCompanyPage setBusinessName(String businessName) {

        fill(BUSINESSNAME_SELECTOR).with(businessName);
        return this;
    }

    public RegisterCompanyPage setEmail(String email) {

        fill(EMAIL_SELECTOR).with(email);
        return this;
    }

    public RegisterCompanyPage setCif(String cif) {

        fill(CIF_SELECTOR).with(cif);
        return this;
    }

    public RegisterCompanyPage selectCountry(String country) {

        if (!country.isEmpty()) {
            Select select = new Select(getDriver().findElement(By.cssSelector(COUNTRY_SELECTOR)));
            select.selectByVisibleText(country);
        }

        waitForJSandJQueryToLoad();

        return this;
    }

    public RegisterCompanyPage selectCity(String city) {

        if (!city.isEmpty()) {
            Select select = new Select(getDriver().findElement(By.cssSelector(CITY_SELECTOR)));
            select.selectByVisibleText(city);
        }

        return this;
    }

    public RegisterCompanyPage setZipcode(String zipcode) {

        fill(ZIPCODE_SELECTOR).with(zipcode);
        return this;
    }

    public RegisterCompanyPage setPhone(String phone) {

        fill(PHONE_SELECTOR).with(phone);
        return this;
    }

    public RegisterCompanyPage setAddress(String address) {

        fill(ADDRESS1_SELECTOR).with(address);
        return this;
    }

    public RegisterCompanyPage selectCategory(String category) {

        if (!category.isEmpty()) {
            Select select = new Select(getDriver().findElement(By.cssSelector(CATEGORY_SELECTOR)));
            select.selectByVisibleText(category);
        }

        return this;
    }

    public RegisterCompanyPage submit() {

        submit(SUBMIT_SELECTOR);
        return this;
    }

    public boolean errorIsShown() {

        return !find(ERROR_SELECTOR).isEmpty();
    }

}
