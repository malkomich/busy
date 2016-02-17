package busy.company;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import busy.AbstractDBTest;

/**
 * Test Case for the RolDao implementation class.
 * 
 * @author malkomich
 *
 */
public class RoleDBTest extends AbstractDBTest {

	@Autowired
	protected RoleDaoImpl repository;
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPersonInvalid() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCompanyNull() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCompanyInvalid() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithWorkerNameTooLong() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithIsManagerNull() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPersonAndCompanyDuplicated() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCompanyAndWorkerNameDuplicated() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPersonAndWorkerNameNull() {
		
	}
	
	@Test
	public void insertWithPersonSuccessfully() {
		
	}
	
	@Test
	public void insertWithWorkerNameSuccessfully() {
		
	}
	
	@Test
	public void insertWithPersonAndWorkerNameSuccessfully() {
		
	}
	
}
