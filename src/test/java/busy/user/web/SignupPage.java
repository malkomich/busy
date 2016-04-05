package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import busy.BusyPage;

/**
 * Page for the user signup form.
 * 
 * @author malkomich
 *
 */
public class SignupPage extends BusyPage {

    private static final String PATH = "/signup";
    private static final String DESCRIPTION = "Signup Page";

    /*
     * CSS Selectors
     */
    private static final String FIRSTNAME_SELECTOR = "#firstname";
    private static final String LASTNAME_SELECTOR = "#lastname";
    private static final String EMAIL_SELECTOR = "#email";
    private static final String COUNTRY_SELECTOR = "#country";
    private static final String CITY_SELECTOR = "#city";
    private static final String ZIPCODE_SELECTOR = "#zipcode";
    private static final String PHONE_SELECTOR = "#phone";
    private static final String PASSWORD_SELECTOR = "#password";
    private static final String PASSCONFIRM_SELECTOR = "#confirmedPassword";

    private static final String SUBMIT_SELECTOR = "#submit";
    private static final String ERROR_SELECTOR = "span.error";

    /*
     * (non-Javadoc)
     * @see busy.BusyPage#relativePath()
     */
    @Override
    public String relativePath() {

        return PATH;
    }

    /*
     * (non-Javadoc)
     * @see org.fluentlenium.core.FluentPage#isAt()
     */
    @Override
    public void isAt() {

        String description = getDriver().findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertThat(description).contains(DESCRIPTION);
    }

    public SignupPage setFirstname(String firstname) {

        fill(FIRSTNAME_SELECTOR).with(firstname);
        return this;
    }

    public SignupPage setLastname(String lastname) {

        fill(LASTNAME_SELECTOR).with(lastname);
        return this;
    }

    public SignupPage setEmail(String email) {

        fill(EMAIL_SELECTOR).with(email);
        return this;
    }

    public SignupPage selectCountry(String country) {

        if (!country.isEmpty()) {
            Select select = new Select(getDriver().findElement(By.cssSelector(COUNTRY_SELECTOR)));
            select.selectByVisibleText(country);
        }

        waitForJSandJQueryToLoad();

        return this;
    }

    public SignupPage selectCity(String city) {

        if (!city.isEmpty()) {
            Select select = new Select(getDriver().findElement(By.cssSelector(CITY_SELECTOR)));
            select.selectByVisibleText(city);
        }

        return this;
    }

    public SignupPage setZipcode(String zipcode) {

        fill(ZIPCODE_SELECTOR).with(zipcode);
        return this;
    }

    public SignupPage setPhoneNumber(String phone) {

        fill(PHONE_SELECTOR).with(phone);
        return this;
    }

    public SignupPage setPassword(String password) {

        fill(PASSWORD_SELECTOR).with(password);
        return this;
    }

    public SignupPage setPasswordConfirmation(String passConfirm) {

        fill(PASSCONFIRM_SELECTOR).with(passConfirm);
        return this;
    }

    public SignupPage submit() {

        submit(SUBMIT_SELECTOR);
        return this;
    }

    public boolean errorIsShown() {

        return !find(ERROR_SELECTOR).isEmpty();
    }

    public boolean emailIsSent() {

        // Nothing to check, the operation is done in back end.
        return true;
    }

}
