package busy.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import busy.AbstractDBTest;

/**
 * Test Case for the CountryDao interface.
 * 
 * @author malkomich
 *
 */
public class CountryDBTest extends AbstractDBTest {

	@Autowired
	private CountryDaoImpl repository;

	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(true)
	public void nameNull() {

		Country country = new Country();
		country.setCode("ES");
		repository.save(country);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(true)
	public void nameTooLong() {

		Country country = new Country();
		country.setName("aaaaabbbbbcccccdddddeeeeefffffgggggh");
		country.setCode("ES");
		repository.save(country);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(true)
	public void nameDuplicated() {

		Country country1 = new Country("España", "ES");
		Country country2 = new Country("España", "US");
		repository.save(country1);
		repository.save(country2);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(true)
	public void codeNull() {

		Country country = new Country();
		country.setName("España");
		repository.save(country);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(true)
	public void codeTooLong() {

		Country country = new Country("España", "ESS");
		repository.save(country);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(true)
	public void codeDuplicated() {

		Country country1 = new Country("España", "ES");
		Country country2 = new Country("EEUU", "ES");
		repository.save(country1);
		repository.save(country2);
	}

	@Test
	@Rollback(true)
	public void insertSuccesfully() {

		Country country = new Country("España", "ES");
		repository.save(country);
		Iterator<Country> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		Country resultCountry = it.next();
		assertEquals("España", resultCountry.getName());
		assertEquals("ES", resultCountry.getCode());
	}

}
