package busy.schedule;

import java.util.List;

import org.springframework.stereotype.Repository;

import busy.company.Company;

/**
 * Service type persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ServiceTypeDaoImpl implements ServiceTypeDao {

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceTypeDao#save(busy.schedule.ServiceType)
     */
    @Override
    public void save(ServiceType serviceType) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceTypeDao#findByCompany(busy.company.Company)
     */
    @Override
    public List<ServiceType> findByCompany(Company company) {
        // TODO Auto-generated method stub
        return null;
    }

}
