package busy.schedule.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.schedule.TimeSlot;

/**
 * Booking Form Validator. It validates if the time slot and role selected for booking an
 * appointment, are still available to perform the operation.
 * 
 * @author malkomich
 *
 */
public class BookingValidator implements Validator {

    private TimeSlot timeSlot;

    public BookingValidator(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return BookingForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BookingForm bookingForm = (BookingForm) target;

        int maxBookings = timeSlot.getService().getServiceType().getMaxBookingsPerRole();

        if (!timeSlot.isAvailable()) {

            errors.rejectValue("dateTime", "schedule.booking.time.full");

            System.out.println(bookingForm.getSchedule());
            System.out.println(bookingForm.getSchedule().getBookings());
        } else if ((bookingForm.getSchedule() != null)
            && (bookingForm.getSchedule().getBookings().size() >= maxBookings)) {

            errors.rejectValue("schedule", "schedule.booking.role.full");
        }

    }

}
