package busy.schedule;

import java.util.List;

import busy.company.Company;

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
     */
    void save(ServiceType serviceType);

    /**
     * Gets all the service types from the given company.
     * 
     * @param company
     *            the company attached to the requested service types
     * @return The list of resultant service types
     */
    List<ServiceType> findByCompany(Company company);

}
