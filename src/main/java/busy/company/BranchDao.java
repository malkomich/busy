package busy.company;

/**
 * Branch persistence interface.
 * 
 * @author malkomich
 */
public interface BranchDao {

    /**
     * Persists a new branch object or updates an existing one.
     * 
     * @param branch
     *            the branch object to be persisted
     */
    void save(Branch branch);

    /**
     * Gets the branch object which ID matches the given one.
     * 
     * @param id
     *            unique ID of the branch
     * @return The resultant branch
     */
    Branch findById(int id);

}
