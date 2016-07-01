package busy.schedule.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import busy.schedule.Schedule;
import busy.schedule.TimeSlot;
import busy.user.User;

/**
 * Booking Form Validator. It validates if the time slot and role selected for booking an
 * appointment, are still available to perform the operation.
 * 
 * @author malkomich
 *
 */
public class BookingValidator implements Validator {

    private TimeSlot timeSlot;
    private User user;

    public BookingValidator(TimeSlot timeSlot, User user) {
        this.timeSlot = timeSlot;
        this.user = user;
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

        } else if ((bookingForm.getSchedule() != null)
            && (bookingForm.getSchedule().getBookings().size() >= maxBookings)) {

            errors.rejectValue("schedule", "schedule.booking.role.full");

        } else {
            for (Schedule schedule : timeSlot.getSchedules()) {
                for (User user : schedule.getBookings()) {
                    if (this.user.equals(user)) {
                        errors.rejectValue("dateTime", "schedule.booking.time.booked");
                    }
                }
            }
        }

    }

}
