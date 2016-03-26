package busy.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Location service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private AddressDao addressDao;

    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    /*
     * (non-Javadoc)
     * @see busy.location.LocationService#findCountries()
     */
    @Override
    public List<Country> findCountries() {

        return countryDao.findAll();

    }

    /*
     * (non-Javadoc)
     * @see busy.location.LocationService#findCitiesByCountry(busy.location.Country)
     */
    @Override
    public List<City> findCitiesByCountryCode(String countryCode) {

        return cityDao.findByCountryCode(countryCode);

    }

    /*
     * (non-Javadoc)
     * @see busy.location.LocationService#findCityById(int)
     */
    @Override
    public City findCityById(int parseInt) {

        return cityDao.findById(parseInt);
    }

    /*
     * (non-Javadoc)
     * @see busy.location.LocationService#saveAddress(busy.location.Address)
     */
    @Override
    public void saveAddress(Address address) {

        addressDao.save(address);
    }

}
