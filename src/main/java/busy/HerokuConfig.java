package busy;

import java.net.URI;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Enable the datasource autowiring for Heroku deployment profile.
 * 
 * @author malkomich
 *
 */
@Configuration
@Profile("heroku")
public class HerokuConfig {

    @Value("${spring.datasource.url}")
    String url;

    /**
     * This is an specific DataSource of a PostGreSQL database in the production environment of
     * Heroku. This bean will be unique for the application, so the scope is defined as Singleton.
     * 
     * @return The data source of the DB attached to Heroku
     * @throws Exception
     *             the uri for the DB path can't be built
     */
    @Bean(name = "dataSource")
    @Scope(value = "singleton")
    public DataSource datasource() throws Exception {

        URI dbUri = new URI(System.getenv(url));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
        return new DriverManagerDataSource(dbUrl, username, password);
    }
}
