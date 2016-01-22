package busy.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private CityDao cityDao;
	
	/* (non-Javadoc)
	 * @see busy.location.LocationService#findCountries()
	 */
	@Override
	public List<Country> findCountries() {
		
		return countryDao.findAll();
		
	}

	/* (non-Javadoc)
	 * @see busy.location.LocationService#findCitiesByCountry(busy.location.Country)
	 */
	@Override
	public List<City> findCitiesByCountryCode(String countryCode) {
		
		return cityDao.findByCountryCode(countryCode);
		
	}

}
