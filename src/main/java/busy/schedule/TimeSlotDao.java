package busy.schedule;

/**
 * Time Slot persistence interface.
 * 
 * @author malkomich
 *
 */
public interface TimeSlotDao {

    /**
     * Persists a new time slot or update an existing one.
     * 
     * @param timeSlot
     *            the Time Slot object to be saved
     * @param serviceId
     *            the unique id of the service to attach the time slot
     */
    void save(TimeSlot timeSlot, int serviceId);

    /**
     * Gets a specific time slot given its ID.
     * 
     * @param id
     *            unique ID of the time slot
     * @return The resultant time slot
     */
    TimeSlot findById(int id);

}
