package busy.user.web;

import static org.fest.assertions.Assertions.assertThat;

import org.springframework.stereotype.Component;

import busy.BusyPage;

/**
 * Page for the user login.
 * 
 * @author malkomich
 *
 */
@Component
public class LoginPage extends BusyPage {

	@Override
	public String relativePath() {
		return "/";
	}

	/* (non-Javadoc)
	 * @see org.fluentlenium.core.FluentPage#isAt()
	 */
	@Override
	public void isAt() {
		assertThat(title()).contains("Mantente ocupado");
	}

	public void setEmail(String email) {
		fill("#inputEmail").with(email);
	}

	public void setPassword(String password) {
		fill("#inputPassword").with(password);
	}

	public void submit() {
		submit("#login-form");
	}

	public boolean errorIsShown() {
		return false;
	}

}
