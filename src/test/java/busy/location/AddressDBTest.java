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
 * Test Case for the AddressDao interface.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("citySet.xml")
public class AddressDBTest extends AbstractDBTest {

	@Autowired
	private AddressDaoImpl repository;

	private static City city;

	@BeforeClass
	public static void initialize() {
		city = new City();
		city.setId(1);
	}

	@Test(expected = Exception.class)
	public void address1TooLong() {
		Address address = new Address();
		address.setAddress1("aaaaabbbbbcccccdddddeeeeefffffgggggh");
		address.setCity(city);
		repository.save(address);
	}

	@Test(expected = Exception.class)
	public void address2TooLong() {
		Address address = new Address();
		address.setAddress2("aaaaabbbbbcccccdddddeeeeefffffgggggh");
		address.setCity(city);
		repository.save(address);
	}

	@Test(expected = Exception.class)
	public void zipCodeTooShort() {
		Address address = new Address();
		address.setZipCode(1);
		address.setCity(city);
		repository.save(address);
	}

	@Test(expected = Exception.class)
	public void zipCodeTooLong() {
		Address address = new Address();
		address.setZipCode(470055);
		address.setCity(city);
		repository.save(address);
	}

	@Test(expected = Exception.class)
	public void cityNull() {
		Address address = new Address("Calle Ejemplo", "1, 1ºA", 47005, null);
		repository.save(address);
	}

	@Test(expected = Exception.class)
	public void cityNotExists() {
		City newCity = new City("París", new Country("Francia", "FR"));
		Address address = new Address("Calle Ejemplo", "1, 1ºA", 47005,
				newCity);
		repository.save(address);
	}

	@Test
	public void insertAddressSuccesfully() {
		Address address = new Address("Calle Ejemplo", "1, 1ºA", 47005, city);
		repository.save(address);
		Iterator<Address> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		Address resultAddress = it.next();
		assertEquals("Calle Ejemplo", resultAddress.getAddress1());
		assertEquals("1, 1ºA", resultAddress.getAddress2());
		assertEquals(new Integer(47005), resultAddress.getZipCode());
		assertEquals(city, resultAddress.getCity());
	}
}
