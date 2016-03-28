package busy.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Company service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BranchDao branchDao;

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setBranchDao(BranchDao branchDao) {
        this.branchDao = branchDao;
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#saveCompany(busy.company.Company)
     */
    @Override
    public void saveCompany(Company company) {

        companyDao.save(company);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#findCategories()
     */
    @Override
    public List<Category> findCategories() {

        return categoryDao.findAll();
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#findCategoryById(int)
     */
    @Override
    public Category findCategoryById(int categoryId) {

        return categoryDao.findById(categoryId);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#saveBranch(busy.company.Branch)
     */
    @Override
    public void saveBranch(Branch branch) {

        branchDao.save(branch);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#allCompanies()
     */
    @Override
    public List<Company> findAllCompanies() {

        return companyDao.findAll();
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#findCompanyById(int)
     */
    @Override
    public Company findCompanyById(int id) {

        return companyDao.findById(id);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#findCompanyByBusinessName(java.lang.String)
     */
    @Override
    public Company findCompanyByBusinessName(String businessName) {

        return companyDao.findByBusinessName(businessName);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#findCompanyByEmail(java.lang.String)
     */
    @Override
    public Company findCompanyByEmail(String email) {

        return companyDao.findByEmail(email);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#findCompanyByCif(java.lang.String)
     */
    @Override
    public Company findCompanyByCif(String cif) {

        return companyDao.findByCif(cif);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#findCompaniesByPartialName(java.lang.String)
     */
    @Override
    public List<Company> findActiveCompaniesByPartialName(String partialName) {

        return companyDao.findActivesByPartialName(partialName);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyService#countAllCompanies()
     */
    @Override
    public int countAllCompanies() {

        return companyDao.countAll();
    }

}
