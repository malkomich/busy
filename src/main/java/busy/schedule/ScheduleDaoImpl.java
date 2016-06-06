package busy.schedule;

import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.ROLE_ID;
import static busy.util.SQLUtil.SERVICE_ID;
import static busy.util.SQLUtil.TABLE_SCHEDULE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.util.SecureSetter;

/**
 * Schedule persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    private static final String SQL_UPDATE =
            "UPDATE " + TABLE_SCHEDULE + " SET " + SERVICE_ID + "= ?," + ROLE_ID + "= ?" + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_SCHEDULE);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(SERVICE_ID, ROLE_ID));

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#save(busy.schedule.Schedule)
     */
    @Override
    public void save(Schedule schedule, int serviceId) {

        if (schedule.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, serviceId, schedule.getRoleId(), schedule.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(SERVICE_ID, serviceId);
            parameters.put(ROLE_ID, schedule.getRoleId());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                SecureSetter.setId(schedule, key.intValue());
            }

        }
    }

}
