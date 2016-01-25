package busy.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

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

	@Test(expected = DataIntegrityViolationException.class)
	public void nameNull() {

		City city = new City();
		city.setCountry(country);
		repository.save(city);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void nameTooLong() {

		City city = new City("aaaaabbbbbcccccdddddeeeeefffffgggggh", country);
		repository.save(city);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void nameDuplicated() {

		City city1 = new City("Valladolid", country);
		City city2 = new City("Valladolid", country);
		repository.save(city1);
		repository.save(city2);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void countryNull() {

		City city = new City();
		city.setName("Valladolid");
		repository.save(city);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void countryNotExists() {

		Country newCountry = new Country("Francia", "FR");
		City city = new City("París", newCountry);
		repository.save(city);
	}

	@Test
	public void addCitySuccessfully() {

		City city = new City("Valladolid", country);
		repository.save(city);
		Iterator<City> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		City resultCity = it.next();
		assertEquals("Valladolid", resultCity.getName());
		assertEquals(new Integer(country.getId()), resultCity.getCountryId());
	}
	
	@Test
	public void findCitiesByCountryWrong() {
		
		String code = "asdf";
		List<City> cityList = repository.findByCountryCode(code);
		assertTrue(cityList.isEmpty());
	}
	
	@Test
	@DatabaseSetup("citySet.xml")
	public void findCitiesByCountrySuccessfully() {
		
		String code = "ES";
		List<City> cityList = repository.findByCountryCode(code);
		assertFalse(cityList.isEmpty());
	}
	
	@Test
	public void findCityByIdWrong() {
		
		int cityId = 0;
		City city = repository.findById(cityId);
		assertNull(city);
	}
	
	@Test
	@DatabaseSetup("citySet.xml")
	public void findCityByIdSuccessfully() {
		
		int cityId = 1;
		City city = repository.findById(cityId);
		assertNotNull(city);
	}
	
	@Test
	public void findAllCitiesWhenEmpty() {
		
		List<City> cityList = repository.findAll();
		assertTrue(cityList.isEmpty());
	}

}
