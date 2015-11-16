package busy;

import java.net.URI;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

/**
 * Basic class configurated to let subclasses easily implement database unit
 * testing using some of the benefits provided by 'DBUnit'.
 * 
 * @author malkomich
 *
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AbstractDBTest {

	/**
	 * Database connection source injected with the configuration values stored
	 * in a properties file.
	 */
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	/**
	 * DBUnit specific object to provide configuration to to properly state the
	 * underlying database
	 */
	private IDatabaseTester databaseTester;

	/**
	 * Prepare the test instance by handling the Spring annotations and updating
	 * the database to the stale state.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		databaseTester = new DataSourceDatabaseTester(dataSource);
		databaseTester.setDataSet(this.getDataSet());
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onSetup();
	}

	/**
	 * Clean the created registries.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
		databaseTester.onTearDown();
	}

	/**
	 * Gets a collection of data organized in tables, wich can be predefined in
	 * a XML file or the whole actual database overturned in a DataSet. In this
	 * case the latter was the chosen option.
	 * 
	 * @return Snapshot of the database
	 * @throws Exception
	 */
	protected IDataSet getDataSet() throws Exception {

		IDatabaseConnection connection = new DatabaseConnection(
				dataSource.getConnection());

		return connection.createDataSet();
	}

}

/**
 * Enable the datasource autowiring for Heroku deployment profile.
 * 
 * @author malkomich
 *
 */
@Configuration
@Profile("heroku")
class HerokuConfig {

	@Value("${spring.datasource.url}")
	protected String url;

	/**
	 * This is an specific DataSource of a PostGreSQL database in the production
	 * environment of Heroku.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "dataSource")
	public DataSource datasource() throws Exception {
		URI dbUri = new URI(System.getenv(url));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":"
				+ dbUri.getPort() + dbUri.getPath();
		return new DriverManagerDataSource(dbUrl, username, password);
	}
}