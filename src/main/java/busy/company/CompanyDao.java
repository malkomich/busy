package busy.company;

public interface CompanyDao {

	/**
	 * Create or update a Company registry in the database.
	 * 
	 * @param company
	 */
	void save(Company company);

}
