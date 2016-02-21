package busy.company;

import java.util.List;

public interface CompanyService {

	/**
	 * Find a Category by his ID
	 * 
	 * @param categoryId
	 * @return
	 */
	Category findCategoryById(int categoryId);

	/**
	 * Saves the Company into the database.
	 * 
	 * @param company
	 */
	void saveCompany(Company company);

	/**
	 * Find a Company by his Business Name
	 * 
	 * @param businessName
	 * @return
	 */
	Company findCompanyByBusinessName(String businessName);

	/**
	 * Find a Company by his Email Address
	 * 
	 * @param email
	 * @return
	 */
	Company findCompanyByEmail(String email);

	/**
	 * Find a Company by his CIF
	 * 
	 * @param cif
	 * @return
	 */
	Company findCompanyByCif(String cif);

	/**
	 * Saves the Branch into the database.
	 * 
	 * @param branch
	 */
	void saveBranch(Branch branch);

	/**
	 * Retrieves the list of all the categories stored in the database.
	 * 
	 * @return
	 */
	List<Category> findCategories();
}
