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
     * @param company
     *            company of the branch offices
     * @return The list of resultant branch offices
     */
    List<Branch> findByCompany(Company company);

    /**
     * Gets the headquarters office of a given company.
     * 
     * @param company
     *            company which headquarters is requested
     * @return The resultant headquarters
     */
    Branch findHeadQuarters(Company company);

}
