package busy.location.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import busy.location.City;
import busy.location.LocationService;

/**
 * Controller for location operations, like update the list of cities.
 * 
 * @author malkomich
 *
 */
@Controller
public class LocationController {

    /**
     * URL Paths.
     */
    private static final String PATH_CITIES_UPDATE = "/get_city_list";

    @Autowired
    private LocationService locationService;

    /**
     * Request to get all cities which belong to the country with the given code.
     * 
     * @param countryCode
     *            code attached to the country
     * @return The list of resultant cities
     */
    @RequestMapping(value = PATH_CITIES_UPDATE, method = RequestMethod.GET)
    public @ResponseBody List<City>
            updateCities(@RequestParam(value = "countryCode", required = true) String countryCode) {

        return locationService.findCitiesByCountryCode(countryCode);
    }
}
