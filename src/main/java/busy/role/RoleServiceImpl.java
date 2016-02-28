package busy.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import busy.user.User;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public void saveRole(Role role) {
		
		roleDao.save(role);
	}
	
	@Override
	public List<Role> findRolesByUser(User user) {

		return roleDao.findByUser(user);
	}

}
