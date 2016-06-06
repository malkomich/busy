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
     * @param serviceId
     *            the unique id of the service to attach the schedules
     */
    void save(Schedule schedule, int serviceId);

}
