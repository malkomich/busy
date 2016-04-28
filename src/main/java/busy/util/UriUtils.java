package busy.util;

import org.springframework.core.env.Environment;

public class UriUtils {

    private static final String PROFILE_PROD = "prod";

    private static final String ROOT_PATH_DEFAULT = "http://localhost:8080";
    private static final String ROOT_PATH_PROD = "https://busy-web.herokuapp.com";

    /**
     * Gets the URI string of the root path of the application, depending the active profile.
     * 
     * @param env
     *            Spring environment instance
     * @return The actual root path
     */
    public static String getRootPath(Environment env) {

        String[] profiles = env.getActiveProfiles();
        for (String profile : profiles) {
            if (PROFILE_PROD.equals(profile)) {
                return ROOT_PATH_PROD;
            }
        }
        return ROOT_PATH_DEFAULT;
    }
}
