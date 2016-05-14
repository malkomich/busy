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
     */
    void save(Schedule schedule);

}
