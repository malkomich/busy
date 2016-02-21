package busy.company;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import busy.AbstractDBTest;

/**
 * Test Case for the CategoryDao implementation class.
 * 
 * @author malkomich
 *
 */
public class CategoryDBTest extends AbstractDBTest {

	@Autowired
	private CategoryDaoImpl repository;
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithNameNull() {
		
		Category category = new Category();
		repository.save(category);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithNameTooLong() {
		
		Category category = new Category();
		category.setName("Abcdefghijklmnñopqrstuvwxyz Abc");
		repository.save(category);
	}
	
	@Test
	public void insertSuccessfully() {
		
		Category category = new Category("Mobiliario");
		repository.save(category);
	}
}
