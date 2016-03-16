package busy.company;

import java.util.List;

public interface CompanyDao {

	/**
	 * Create or update a Company registry in the database.
	 * 
	 * @param company
	 */
	void save(Company company);

	/**
	 * Finds all the existing Companies, or an empty list when there is not any
	 * Company.
	 * 
	 * @return
	 */
	List<Company> findAll();

	/**
	 * Finds the Company in the database which id match the given one. It will
	 * return null when no Companies fulfill the restriction.
	 * 
	 * @param id
	 * @return
	 */
	Company findById(int id);

	/**
	 * Finds the Company in the database which Business Name match the given
	 * one. It will return null when no Companies fulfill the restriction.
	 * 
	 * @param businessName
	 * @return
	 */
	Company findByBusinessName(String businessName);

	/**
	 * Finds the Company in the database which email match the given one. It
	 * will return null when no Companies fulfill the restriction.
	 * 
	 * @param email
	 * @return
	 */
	Company findByEmail(String email);

	/**
	 * Finds the Company in the database which CIF match the given one. It will
	 * return null when no Companies fulfill the restriction.
	 * 
	 * @param cif
	 * @return
	 */
	Company findByCif(String cif);

	/**
	 * Finds the Companies in the database which tradeName or businessName
	 * contains the given partialName. It will return an empty list when no
	 * Companies fulfill the restriction.
	 * 
	 * @param partialName
	 * @return
	 */
	List<Company> findByPartialName(String partialName);

	/**
	 * Counts the number of all the Companies.
	 * 
	 * @return
	 */
	int countAll();

}
