package busy.role;

import java.util.List;

import busy.user.User;

public interface RoleDao {

	void save(Role role);

	List<Role> findByUser(User user);

}
