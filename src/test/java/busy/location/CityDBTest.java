package busy.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;

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

	private static Country country;

	@BeforeClass
	public static void initialize() {
		country = new Country();
		country.setId(1);
	}

	@Test(expected = Exception.class)
	public void nameNull() {
		City city = new City();
		city.setCountry(country);
		repository.save(city);
	}

	@Test(expected = Exception.class)
	public void nameTooLong() {
		City city = new City("aaaaabbbbbcccccdddddeeeeefffffgggggh", country);
		repository.save(city);
	}

	@Test(expected = Exception.class)
	public void nameDuplicated() {
		City city1 = new City("Valladolid", country);
		City city2 = new City("Valladolid", country);
		repository.save(city1);
		repository.save(city2);
	}

	@Test(expected = Exception.class)
	public void nameInvalidCharacters() {
		City city = new City("A1", country);
		repository.save(city);
	}

	@Test(expected = Exception.class)
	public void countryNull() {
		City city = new City();
		city.setName("Valladolid");
		repository.save(city);
	}

	@Test(expected = Exception.class)
	public void countryNotExists() {
		Country newCountry = new Country("Francia", "FR");
		City city = new City("París", newCountry);
		repository.save(city);
	}

	@Test
	public void addCitySuccesful() {
		City city = new City("Valladolid", country);
		repository.save(city);
		Iterator<City> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		City resultCity = it.next();
		assertEquals("Valladolid", resultCity.getName());
		assertEquals(country, resultCity.getCountry());
	}

}
