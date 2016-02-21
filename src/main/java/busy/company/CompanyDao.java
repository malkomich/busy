package busy.company;

public interface CompanyDao {

	/**
	 * Create or update a Company registry in the database.
	 * 
	 * @param company
	 */
	void save(Company company);

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

}
