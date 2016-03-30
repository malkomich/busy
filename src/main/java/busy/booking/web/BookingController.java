package busy.booking.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import busy.booking.Booking;
import busy.booking.BookingService;
import busy.company.Branch;
import busy.company.CompanyService;

/**
 * Controller for booking operations.
 * 
 * @author malkomich
 *
 */
@Controller
public class BookingController {

    /**
     * URL Paths.
     */
    private static final String PATH_BOOKINGS_OF_MONTH = "/get_month_bookings";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CompanyService companyService;

    /**
     * Request to get all bookings made in the specific month of the given branch.
     * 
     * @param branchId
     *            the branch attached to the requested bookings
     * @param year
     *            the year which the month belong to
     * @param month
     *            the month which bookings are requested
     * @return The list of resultant bookings
     */
    @RequestMapping(value = PATH_BOOKINGS_OF_MONTH, method = RequestMethod.GET)
    public @ResponseBody List<Booking> getMonthBookings(
            @RequestParam(value = "branchId", required = true) String branchId,
            @RequestParam(value = "year", required = true) String year,
            @RequestParam(value = "month", required = true) String month) {

        Branch branch = companyService.findBranchById(Integer.parseInt(branchId));

        return bookingService.findBookingsByBranchAndYearAndMonth(branch, Integer.parseInt(year),
                Integer.parseInt(month));
    }
}
