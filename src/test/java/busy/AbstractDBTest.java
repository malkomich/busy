package busy;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

/**
 * Basic class configurated to let subclasses easily implement database unit testing.
 * 
 * @author malkomich
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class })
public class AbstractDBTest {

    protected static final Integer INVALID_ID = 999;

}
