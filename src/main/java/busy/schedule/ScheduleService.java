package busy.schedule;

import java.util.List;

import busy.company.Branch;
import busy.company.Company;
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
     * Gets the schedule of a complete year of a specific branch.
     * 
     * @param branch
     *            the branch attached to the requested schedule
     * @param year
     *            the number of year which schedule is requested
     * @return The resultant year schedule
     */
    YearSchedule findScheduleByBranch(Branch branch, int year);

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
}
