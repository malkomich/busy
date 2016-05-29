package busy.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveRole(Role role) {

        roleDao.save(role);
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleService#findRolesByUser(busy.user.User)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findRolesByUser(User user) {

        return roleDao.findByUser(user);
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleService#findCompanyManager(busy.company.Company)
     */
    @Override
    @Transactional(readOnly = true)
    public Role findCompanyManager(Company company) {

        return roleDao.findManagerByCompany(company);
    }

    /* (non-Javadoc)
     * @see busy.role.RoleService#findRoleById(int)
     */
    @Override
    @Transactional(readOnly = true)
    public Role findRoleById(int id) {

        return roleDao.findById(id);
    }

}
