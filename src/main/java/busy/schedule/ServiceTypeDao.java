package busy.schedule;

import java.util.List;

import busy.company.Company;
import busy.util.OperationResult;

/**
 * Service type persistence interface.
 * 
 * @author malkomich
 *
 */
public interface ServiceTypeDao {

    /**
     * Persists a new service type object or updates an existing one.
     * 
     * @param serviceType
     *            The service type object to be persisted
     * @return The service type saved
     */
    ServiceType save(ServiceType serviceType);

    /**
     * Gets all the service types from the given company.
     * 
     * @param company
     *            the company attached to the requested service types
     * @return The list of resultant service types
     */
    List<ServiceType> findByCompany(Company company);

    /**
     * Deletes the specified service type object.
     * 
     * @param sType
     *            the service type to delete
     * @return The result of the operation
     */
    OperationResult delete(ServiceType sType);

    /**
     * Gets the service type with the given name and company, if it exists.
     * 
     * @param company
     *            the company which service type is attached to
     * @param name
     *            the name of the type
     * @return The resultant service type
     */
    ServiceType findByCompanyAndName(Company company, String name);

}
