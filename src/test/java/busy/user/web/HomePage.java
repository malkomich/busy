package busy.user.web;

import busy.BusyPage;

/**
 * Page for the main page of Busy.
 * 
 * @author malkomich
 *
 */
public class HomePage extends BusyPage {

	@Override
	public String relativePath() {
		return "/";
	}

	@Override
	public void isAt() {
		assert (title()).contains("Página Principal");
	}
	
}
