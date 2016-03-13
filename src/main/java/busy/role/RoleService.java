package busy.role;

import java.util.List;

import busy.company.Company;
import busy.user.User;

public interface RoleService {

	/**
	 * Save a Role item to the database.
	 * 
	 * @param role
	 */
	void saveRole(Role role);
	
	/**
	 * Get all roles of the given User.
	 * 
	 * @param user
	 * @return
	 */
	List<Role> findRolesByUser(User user);
	
	/**
	 * Retrieves the main role of the given Company, typically the manager or
	 * the person in charge of the Company.
	 * 
	 * @param company
	 * @return
	 */
	Role findCompanyManager(Company company);

}
