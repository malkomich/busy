package busy.schedule;

import java.util.List;

import busy.company.Branch;
import busy.company.Company;

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
}
