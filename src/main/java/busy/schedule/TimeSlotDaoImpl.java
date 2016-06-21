package busy.schedule;

import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.SERVICE_ID;
import static busy.util.SQLUtil.START_TIME;
import static busy.util.SQLUtil.TABLE_TIME_SLOT;

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

@Repository
public class TimeSlotDaoImpl implements TimeSlotDao {

    private static final String SQL_UPDATE =
        "UPDATE " + TABLE_TIME_SLOT + " SET " + START_TIME + "= ?," + SERVICE_ID + "= ?" + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_TIME_SLOT);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(START_TIME, SERVICE_ID));

    }

    @Override
    public void save(TimeSlot timeSlot, int serviceId) {

        if (timeSlot.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, timeSlot.getStartTimestamp(), serviceId, timeSlot.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(START_TIME, timeSlot.getStartTimestamp());
            parameters.put(SERVICE_ID, serviceId);

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                SecureSetter.setId(timeSlot, key.intValue());
            }

        }
    }

}
