package busy.company;

import java.util.List;

/**
 * Branch persistence interface.
 * 
 * @author malkomich
 */
public interface BranchDao {

    /**
     * Persists a new branch object or updates an existing one.
     * 
     * @param branch
     *            the branch object to be persisted
     */
    void save(Branch branch);

    /**
     * Gets the branch object which ID matches the given one.
     * 
     * @param id
     *            unique ID of the branch
     * @return The resultant branch
     */
    Branch findById(int id);

    /**
     * Gets the list of branch offices from the given company.
     * 
     * @param companyId
     *            unique ID of the company with the branch offices requested
     * @return The list of resultant branch offices
     */
    List<Branch> findByCompanyId(int companyId);

    /**
     * Gets the headquarters office of a given company.
     * 
     * @param company
     *            company which headquarters is requested
     * @return The resultant headquarters
     */
    Branch findHeadQuarters(Company company);

}
