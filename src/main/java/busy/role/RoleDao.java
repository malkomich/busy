package busy.role;

import java.util.List;

import busy.company.Company;
import busy.user.User;

public interface RoleDao {

	/**
	 * Save the given Role into the database, creating a new one or updating it
	 * if already exists.
	 * 
	 * @param role
	 */
	void save(Role role);

	/**
	 * Gets the list of Roles attached to the given User.
	 * 
	 * @param user
	 * @return
	 */
	List<Role> findByUser(User user);

	/**
	 * Gets the main Role of the given Company.
	 * 
	 * @param company
	 * @return
	 */
	Role findManagerByCompany(Company company);

}
