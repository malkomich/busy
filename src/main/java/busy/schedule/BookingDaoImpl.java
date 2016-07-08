package busy.schedule;

import static busy.util.SQLUtil.SCHEDULE_ID;
import static busy.util.SQLUtil.TABLE_BOOKING;
import static busy.util.SQLUtil.USERID;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.user.User;

/**
 * Booking persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class BookingDaoImpl implements BookingDao {

    private SimpleJdbcInsert jdbcInsert;
    
    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_BOOKING);
        jdbcInsert.setColumnNames(Arrays.asList(USERID, SCHEDULE_ID));

    }
    
    /* (non-Javadoc)
     * @see busy.schedule.BookingDao#save(busy.schedule.Schedule, busy.user.User)
     */
    @Override
    public void save(User userBooking, Schedule schedule) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(USERID, userBooking.getId());
        parameters.put(SCHEDULE_ID, schedule.getId());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
        
        schedule.addBooking(userBooking);
    }

}
