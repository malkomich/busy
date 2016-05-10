package busy.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import busy.company.Company;
import busy.user.User;

/**
 * Role service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /*
     * (non-Javadoc)
     * @see busy.role.RoleService#saveRole(busy.role.Role)
     */
    @Override
    public void saveRole(Role role) {

        roleDao.save(role);
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleService#findRolesByUser(busy.user.User)
     */
    @Override
    public List<Role> findRolesByUser(User user) {

        return roleDao.findByUser(user);
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleService#findCompanyManager(busy.company.Company)
     */
    @Override
    public Role findCompanyManager(Company company) {

        return roleDao.findManagerByCompany(company);
    }

    /* (non-Javadoc)
     * @see busy.role.RoleService#findRoleById(int)
     */
    @Override
    public Role findRoleById(int id) {

        return roleDao.findById(id);
    }

}
