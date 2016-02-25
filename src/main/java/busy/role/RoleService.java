package busy.role;

import java.util.List;

import busy.user.User;

public interface RoleService {

	/**
	 * Get all roles of the given User.
	 * 
	 * @param user
	 * @return
	 */
	List<Role> findRolesByUser(User user);

}
