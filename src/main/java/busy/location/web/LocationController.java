package busy.location.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import busy.location.City;
import busy.location.LocationService;

@Controller
public class LocationController {

	/**
	 * URL Paths.
	 */
	private static final String PATH_CITIES_UPDATE = "get_city_list";
	
	@Autowired
	private LocationService locationService;
	
	@RequestMapping(value = PATH_CITIES_UPDATE, method = RequestMethod.GET)
	public @ResponseBody List<City> updateCities(
			@RequestParam(value = "countryCode", required = true) String countryCode, ModelMap modelMap) {

		return locationService.findCitiesByCountryCode(countryCode);
	}
}
