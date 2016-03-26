package busy.company;

import java.util.List;

/**
 * Company persistence interface.
 * 
 * @author malkomich
 */
public interface CompanyDao {

    /**
     * Persists a new company object or updates an existing one.
     * 
     * @param company
     *            The company object to be persisted
     */
    void save(Company company);

    /**
     * Gets the list of all current company objects.
     * 
     * @return The list of current companies
     */
    List<Company> findAll();

    /**
     * Gets the company object which ID matches the given one.
     * 
     * @param companyId
     *            unique ID of the company
     * @return The resultant company object
     */
    Company findById(int companyId);

    /**
     * Gets the company object which business name matches the given one.
     * 
     * @param businessName
     *            business name of the company
     * @return The resultant company object
     */
    Company findByBusinessName(String businessName);

    /**
     * Gets the company object which email matches the given one.
     * 
     * @param email
     *            email of the company
     * @return The resultant company object
     */
    Company findByEmail(String email);

    /**
     * Gets the company object which CIF matches the given one.
     * 
     * @param cif
     *            CIF of the company
     * @return The resultant company object
     */
    Company findByCif(String cif);

    /**
     * Gets the list of company object which trade name or business name contains the given partial
     * name.
     * 
     * @param partialName
     *            pattern to find the complete name of a company
     * @return The list of resultant companies
     */
    List<Company> findActivesByPartialName(String partialName);

    /**
     * Counts the number of all current company objects.
     * 
     * @return The number of occurrences
     */
    int countAll();

}
