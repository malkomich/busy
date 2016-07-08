package busy.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import busy.company.Branch;
import busy.company.BranchDao;
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
    
    @Autowired
    private BranchDao branchDao;

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

        Branch headquarters = branchDao.findHeadQuarters(company);
        
        return findManagerOfBranch(headquarters);
    }
    
    /* (non-Javadoc)
     * @see busy.role.RoleService#findManagerOfBranch(busy.company.Branch)
     */
    @Override
    public Role findManagerOfBranch(Branch branch) {

        return roleDao.findManagerOfBranch(branch);
    }

    /* (non-Javadoc)
     * @see busy.role.RoleService#findRoleById(int)
     */
    @Override
    @Transactional(readOnly = true)
    public Role findRoleById(int id) {

        return roleDao.findById(id).get(0);
    }
    
    /* (non-Javadoc)
     * @see busy.role.RoleService#findRolesById(java.lang.Object[])
     */
    @Override
    public List<Role> findRolesById(Integer[] roleIds) {
        
        return roleDao.findById(roleIds);
    }

    /* (non-Javadoc)
     * @see busy.role.RoleService#findRolesByBranch(busy.company.Branch)
     */
    @Override
    public List<Role> findRolesByBranch(Branch branch) {

        return roleDao.findByBranch(branch);
    }

}
