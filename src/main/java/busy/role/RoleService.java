package busy.role;

import java.util.List;

import busy.company.Company;
import busy.user.User;

/**
 * Role service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface RoleService {

    /**
     * Saves or updates a role.
     * 
     * @param role
     *            the role to be saved
     */
    void saveRole(Role role);

    /**
     * Gets all roles attached to the given user.
     * 
     * @param user
     *            the user which list of returning roles are attached to
     * @return The list of resultant roles
     */
    List<Role> findRolesByUser(User user);

    /**
     * Gets the main role of the given company, typically the manager or the person in charge of the
     * company.
     * 
     * @param company
     *            the company which role are attached to with manager status
     * @return The resultant role
     */
    Role findCompanyManager(Company company);
    
    /**
     * Gets a role by his ID
     * 
     * @param id
     *            unique ID of the role
     * @return The resultant role
     */
    Role findRoleById(int id);

}
