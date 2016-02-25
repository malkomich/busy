package busy.role;

import java.util.List;

public interface RoleDao {

	void save(Role role);

	List<Role> findByUserId(int userId);

}
