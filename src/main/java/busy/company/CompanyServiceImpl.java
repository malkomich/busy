package busy.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Override
	public void saveCompany(Company company) {
		companyDao.save(company);
	}

	@Override
	public List<Category> findCategories() {
		return categoryDao.findAll();
	}
	
	@Override
	public Category findCategoryById(int categoryId) {
		return categoryDao.findById(categoryId);
	}

	@Override
	public void saveBranch(Branch branch) {
		branchDao.save(branch);
	}

	@Override
	public Company findCompanyByBusinessName(String businessName) {
		return companyDao.findByBusinessName(businessName);
	}

	@Override
	public Company findCompanyByEmail(String email) {
		return companyDao.findByEmail(email);
	}

	@Override
	public Company findCompanyByCif(String cif) {
		return companyDao.findByCif(cif);
	}

}
