package busy.company;

import java.util.List;

/**
 * Company service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface CompanyService {

    /**
     * Gets a category by his ID.
     * 
     * @param categoryId
     *            unique ID of the category
     * @return The resultant category
     */
    Category findCategoryById(int categoryId);

    /**
     * Saves or updates a company.
     * 
     * @param company
     *            the company to be saved
     */
    void saveCompany(Company company);

    /**
     * Gets all the companies currently available.
     * 
     * @return The list of current companies
     */
    List<Company> findAllCompanies();

    /**
     * Gets a company by his ID
     * 
     * @param id
     *            unique ID of the company
     * @return The resultant company
     */
    Company findCompanyById(int id);

    /**
     * Gets a company by his business name
     * 
     * @param businessName
     *            the business name of the company
     * @return The resultant company
     */
    Company findCompanyByBusinessName(String businessName);

    /**
     * Gets a company by his email address
     * 
     * @param email
     *            email address of the company
     * @return The resultant company
     */
    Company findCompanyByEmail(String email);

    /**
     * Gets a company by his CIF
     * 
     * @param cif
     *            CIF of the company
     * @return The resultant company
     */
    Company findCompanyByCif(String cif);

    /**
     * Gets all the companies which name contains the given partial name
     * 
     * @param partialName
     *            the pattern to find company names which contains it
     * @return The list of resultant companies
     */
    List<Company> findActiveCompaniesByPartialName(String partialName);

    /**
     * Saves or updates a branch.
     * 
     * @param branch
     *            the branch to be saved
     */
    void saveBranch(Branch branch);

    /**
     * Gets a branch by his ID
     * 
     * @param id
     *            unique ID of the branch
     * @return The resultant branch
     */
    Branch findBranchById(int id);

    /**
     * Gets the list of all the categories currently available.
     * 
     * @return The list of current categories
     */
    List<Category> findCategories();

    /**
     * Gets the number of companies currently available.
     * 
     * @return The number of occurrences
     */
    int countAllCompanies();

    /**
     * Get the list of branch offices of a given company.
     * 
     * @param companyId
     *            unique ID of the company
     * @return The list of resultant branch offices
     */
    List<Branch> findBranchesByCompanyId(int companyId);

}
