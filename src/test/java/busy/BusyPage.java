package busy;

import org.fluentlenium.core.FluentPage;

/**
 * Extension of FluentPage which enable relative path delegating to Spring the
 * assignment of the root path of the URL based on the deployment Profile.
 * 
 * @author malkomich
 *
 */
public abstract class BusyPage extends FluentPage {

	private String url;

	public void setUrl(String rootUrl) {
		String path = relativePath();
		if(path == null)
			path = "/";
		url = rootUrl + relativePath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fluentlenium.core.FluentPage#getUrl()
	 */
	@Override
	public String getUrl() {
		return url;
	}

	public abstract String relativePath();

}
