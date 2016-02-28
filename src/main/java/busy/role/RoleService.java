package busy.role;

import java.util.List;

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

}
