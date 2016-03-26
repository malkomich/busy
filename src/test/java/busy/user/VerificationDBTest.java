package busy.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;

/**
 * Test Case for the UserDao interface.
 * 
 * @author malkomich
 *
 */
public class VerificationDBTest extends AbstractDBTest {

    @Autowired
    protected VerificationDaoImpl repository;

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithUserNull() {
        repository.save(0, "tokenValid");
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DatabaseSetup("userSet.xml")
    public void insertWithTokenNull() {
        repository.save(1, null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DatabaseSetup("userSet.xml")
    @DatabaseSetup("verificationSet.xml")
    public void insertWithUserDuplicated() {
        repository.save(1, "newToken");
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DatabaseSetup("userSet.xml")
    @DatabaseSetup("verificationSet.xml")
    public void insertWithTokenDuplicated() {
        repository.save(2, "tokenValid");
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DatabaseSetup("userSet.xml")
    public void insertWithInvalidUser() {
        repository.save(3, "tokenValid");
    }

    @Test
    @DatabaseSetup("userSet.xml")
    public void insertSuccessfully() {
        repository.save(1, "tokenValid");
    }

    @Test
    @DatabaseSetup("userSet.xml")
    @DatabaseSetup("verificationSet.xml")
    public void findVerificationByTokenWrong() {

        Verification verification = repository.findByToken("asdf");
        assertNull(verification);
    }

    @Test
    @DatabaseSetup("userSet.xml")
    @DatabaseSetup("verificationSet.xml")
    public void findVerificationByTokenSuccessfully() {

        Verification verification = repository.findByToken("tokenValid");
        assertNotNull(verification);
        assertEquals("user@domain.com", verification.getUser().getEmail());
    }
}
