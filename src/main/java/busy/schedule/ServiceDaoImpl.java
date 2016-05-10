package busy.schedule;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import busy.role.Role;

/**
 * Service persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ServiceDaoImpl implements ServiceDao {

    /* (non-Javadoc)
     * @see busy.schedule.ServiceDao#findBetweenDays(org.joda.time.DateTime, org.joda.time.DateTime, busy.role.Role, busy.schedule.ServiceType)
     */
    @Override
    public List<Service> findBetweenDays(DateTime initDay, DateTime endDay, Role role, ServiceType serviceType) {
        // TODO Auto-generated method stub
        return null;
    }


}
