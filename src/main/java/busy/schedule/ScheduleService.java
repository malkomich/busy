package busy.schedule;

import java.util.List;

import org.joda.time.DateTime;

import busy.company.Company;
import busy.role.Role;
import busy.util.OperationResult;

/**
 * Schedule service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface ScheduleService {

    /**
     * Gets the list of service types attached to the given company.
     * 
     * @param company
     *            company which service types are requested
     * @return The list of resultant service types
     */
    List<ServiceType> findServiceTypesByCompany(Company company);

    /**
     * Deletes the service type given.
     * 
     * @param serviceTypeId
     *            the service type to delete
     * @return The result of the operation
     */
    OperationResult deleteServiceType(ServiceType serviceType);

    /**
     * Gets the service type with the given name and company, if it exists.
     * 
     * @param company
     *            the company which service type is attached to
     * @param name
     *            the name of the type
     * @return The resultant service type
     */
    ServiceType findServiceType(Company company, String name);

    /**
     * Saves or updates a service type.
     * 
     * @param sType
     *            the service type to be saved
     * @return The service type saved
     */
    ServiceType saveServiceType(ServiceType sType);

    /**
     * Gets the list of the scheduled services between the given days, for a given role, and of a
     * given service type. If the role or the service type are null, the services will be found for
     * all roles or of all service types, respectively.
     * 
     * @param fromDateTime
     *            the date time of the initial day of the period in which find services
     * @param toDateTime
     *            the date time of the ending day of the period in which find services
     * @param role
     *            the role attached to the requested services
     * @param serviceType
     *            the type of services wanted
     * @return The resultant list of services
     */
    List<Service> findServicesBetweenDays(DateTime fromDateTime, DateTime toDateTime, Role role,
        ServiceType serviceType);

    /**
     * Saves or updates a map of service lists. The services are grouped by the correlation, which
     * indicates that a list of services are result of a repetition of one of them.
     * 
     * @param services
     *            the map of services to be saved
     */
    void saveServices(List<Service> services);

    /**
     * Gets a specific service type given an ID.
     * 
     * @param parseInt
     *            unique id of the service type
     * @return The resultant service type
     */
    ServiceType findServiceTypeById(int id);
}
