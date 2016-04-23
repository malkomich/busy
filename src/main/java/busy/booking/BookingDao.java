package busy.booking;

import java.util.List;
import java.util.Map;

import busy.schedule.Service;

/**
 * Booking persistence interface.
 * 
 * @author malkomich
 *
 */
public interface BookingDao {

    /**
     * Gets the map which link the given services with its lists of bookings made on them
     * 
     * @param serviceList
     *            list of services which bookings are requested
     * @return Map with the lists of bookings associated to each service
     */
    Map<Service, List<Booking>> findByServices(List<Service> serviceList);

}
