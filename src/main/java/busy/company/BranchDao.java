package busy.company;

public interface BranchDao {

	/**
	 * Create or update a Branch registry in the database.
	 * 
	 * @param branch
	 */
	void save(Branch branch);

}
