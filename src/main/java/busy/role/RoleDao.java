package busy.role;

import java.util.List;

import busy.company.Branch;
import busy.company.Company;
import busy.user.User;

/**
 * Role persistence interface.
 * 
 * @author malkomich
 */
public interface RoleDao {

    /**
     * Persists a new role or update an existing one.
     * 
     * @param role
     *            the role object to be persisted
     */
    void save(Role role);

    /**
     * Gets the list of roles attached to the given user.
     * 
     * @param user
     *            user which list of returning roles are attached to
     * @return The list of role objects
     */
    List<Role> findByUser(User user);

    /**
     * Gets the main role (manager) of the given company.
     * 
     * @param company
     *            company which role are attached to with manager status
     * @return The resultant role object
     */
    Role findManagerByCompany(Company company);

    /**
     * Gets the list of role objects by their IDs.
     * 
     * @param id
     *            unique IDs of the roles
     * @return The resultant list of role objects
     */
    List<Role> findById(Integer... ids);

    /**
     * Gets all roles attached to the given branch.
     * 
     * @param branch
     *            the company branch to find in
     * @return The list of resultant roles
     */
    List<Role> findByBranch(Branch branch);

}
