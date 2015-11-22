package busy.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.util.SecureSetter;

/**
 * Test Case for the CityDao interface.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("countrySet.xml")
public class CityDBTest extends AbstractDBTest {

	@Autowired
	private CityDaoImpl repository;

	private Country country;

	@Before
	public void setUp() {
		country = new Country();
		SecureSetter.setId(country, 1);
	}

	@Test(expected = Exception.class)
	@Rollback(true)
	public void nameNull() {

		City city = new City();
		city.setCountry(country);
		repository.save(city);
	}

	@Test(expected = Exception.class)
	@Rollback(true)
	public void nameTooLong() {

		City city = new City("aaaaabbbbbcccccdddddeeeeefffffgggggh", country);
		repository.save(city);
	}

	@Test(expected = Exception.class)
	@Rollback(true)
	public void nameDuplicated() {

		City city1 = new City("Valladolid", country);
		City city2 = new City("Valladolid", country);
		repository.save(city1);
		repository.save(city2);
	}

	@Test(expected = Exception.class)
	@Rollback(true)
	public void countryNull() {

		City city = new City();
		city.setName("Valladolid");
		repository.save(city);
	}

	@Test(expected = Exception.class)
	@Rollback(true)
	public void countryNotExists() {

		Country newCountry = new Country("Francia", "FR");
		City city = new City("París", newCountry);
		repository.save(city);
	}

	@Test
	@Rollback(true)
	public void addCitySuccesful() {

		City city = new City("Valladolid", country);
		repository.save(city);
		Iterator<City> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		City resultCity = it.next();
		assertEquals("Valladolid", resultCity.getName());
		assertEquals(new Integer(country.getId()), resultCity.getCountryId());
	}

}
