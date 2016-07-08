package busy.schedule;

/**
 * Schedule persistence interface.
 * 
 * @author malkomich
 *
 */
public interface ScheduleDao {

    /**
     * Persists a new schedule or update an existing one.
     * 
     * @param schedule
     *            the schedule object to be saved
     * @param timeSlotId
     *            the unique id of the time slot to attach the schedule
     */
    void save(Schedule schedule, int timeSlotId);

    /**
     * Gets a specific schedule given its ID.
     * 
     * @param id
     *            unique ID of the schedule
     * @return The resultant schedule
     */
    Schedule findById(Integer id);

}
