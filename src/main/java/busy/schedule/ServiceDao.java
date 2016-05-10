package busy.schedule;

import java.util.List;

import org.joda.time.DateTime;

import busy.role.Role;

/**
 * Service persistence interface.
 * 
 * @author malkomich
 *
 */
public interface ServiceDao {

    /**
     * Gets the list of the scheduled services between the given days, for a given role, and of a
     * given service type. If the role or the service type are null, the services will be found for
     * all roles or of all service types, respectively.
     * 
     * @param initDay
     *            the initial day of the period in which find services
     * @param endDay
     *            the ending day of the period in which find services
     * @param role
     *            the role attached to the requested services
     * @param serviceType
     *            the type of services wanted
     * @return The resultant list of services
     */
    List<Service> findBetweenDays(DateTime initDay, DateTime endDay, Role role, ServiceType serviceType);

}
