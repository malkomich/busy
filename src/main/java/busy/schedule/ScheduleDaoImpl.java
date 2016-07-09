package busy.schedule;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADMIN;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.ALIAS_ROLE_ID;
import static busy.util.SQLUtil.ALIAS_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_TIME_SLOT_ID;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.BOOKING_USER_ACTIVE;
import static busy.util.SQLUtil.BOOKING_USER_ADMIN;
import static busy.util.SQLUtil.BOOKING_USER_EMAIL;
import static busy.util.SQLUtil.BOOKING_USER_FIRSTNAME;
import static busy.util.SQLUtil.BOOKING_USER_ID;
import static busy.util.SQLUtil.BOOKING_USER_LASTNAME;
import static busy.util.SQLUtil.BOOKING_USER_NIF;
import static busy.util.SQLUtil.BOOKING_USER_PHONE;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.IS_MANAGER;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.ROLE_ID;
import static busy.util.SQLUtil.SCHEDULE_QUERY;
import static busy.util.SQLUtil.TABLE_SCHEDULE;
import static busy.util.SQLUtil.TIME_SLOT_ID;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.role.Role;
import busy.user.User;
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
        "UPDATE " + TABLE_SCHEDULE + " SET " + TIME_SLOT_ID + "= ?," + ROLE_ID + "= ?" + " WHERE " + ID + "= ?";

    private static final String SQL_SELECT_BY_ID = SCHEDULE_QUERY + " WHERE " + TABLE_SCHEDULE + "." + ID + "=?";

    private static final String RECOVER_ID = "SELECT scheduleQuery." + ALIAS_SCHEDULE_ID + " FROM (" + SCHEDULE_QUERY
        + ") as scheduleQuery WHERE scheduleQuery." + ALIAS_TIME_SLOT_ID + "=? AND scheduleQuery." + ALIAS_ROLE_ID
        + "=?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_SCHEDULE);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(TIME_SLOT_ID, ROLE_ID));

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#save(busy.schedule.Schedule)
     */
    @Override
    public void save(Schedule schedule, int timeSlot) {

        if (schedule.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, timeSlot, schedule.getRoleId(), schedule.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(TIME_SLOT_ID, timeSlot);
            parameters.put(ROLE_ID, schedule.getRoleId());

            Number key;
            // Only save if there is not a previous schedule with same data stored
            try {
                key = jdbcTemplate.queryForObject(RECOVER_ID, parameters.values().toArray(), Integer.class);
            } catch (EmptyResultDataAccessException e) {
                key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
                if (key != null) {
                    schedule.setId(key.intValue());
                }
            }

        }
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findById(java.lang.Integer)
     */
    @Override
    public Schedule findById(Integer id) {

        try {

            return jdbcTemplate.query(SQL_SELECT_BY_ID, new ScheduleExtractor(), id);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    private class ScheduleExtractor implements ResultSetExtractor<Schedule> {

        @Override
        public Schedule extractData(ResultSet rs) throws SQLException {

            boolean hasNext = rs.next();

            Schedule schedule = new Schedule();
            schedule.setId(rs.getInt(ALIAS_SCHEDULE_ID));

            Role role = new Role();
            role.setId(rs.getInt(ALIAS_ROLE_ID));

            // Set User
            User roleUser = new User();
            roleUser.setId(rs.getInt(ALIAS_USER_ID));
            roleUser.setFirstName(rs.getString(FIRSTNAME));
            roleUser.setLastName(rs.getString(LASTNAME));
            roleUser.setEmail(rs.getString(EMAIL));
            roleUser.setNif(rs.getString(NIF));
            roleUser.setPhone(rs.getString(PHONE));
            roleUser.setActive(rs.getBoolean(ACTIVE));
            SecureSetter.setAttribute(roleUser, "setAdmin", Boolean.class, rs.getBoolean(ADMIN));

            Integer addressId = 0;
            if ((addressId = rs.getInt(ALIAS_ADDR_ID)) > 0) {

                Address address = new Address();

                address.setId(addressId);
                address.setAddress1(rs.getString(ADDR1));
                address.setAddress2(rs.getString(ADDR2));
                address.setZipCode(rs.getString(ZIPCODE));

                City city = new City();
                city.setId(rs.getInt(ALIAS_CITY_ID));
                city.setName(rs.getString(ALIAS_CITY_NAME));

                Country country = new Country();
                country.setId(rs.getInt(ALIAS_COUNTRY_ID));
                country.setName(rs.getString(ALIAS_COUNTRY_NAME));
                country.setCode(rs.getString(CODE));

                city.setCountry(country);

                address.setCity(city);

                roleUser.setAddress(address);
            }

            role.setUser(roleUser);

            SecureSetter.setAttribute(role, "setManager", Boolean.class, rs.getBoolean(IS_MANAGER));

            schedule.setRole(role);

            do {
                Integer userId = rs.getInt(BOOKING_USER_ID);
                if (userId != 0) {
                    User bookingUser = new User();
                    bookingUser.setId(userId);
                    bookingUser.setFirstName(rs.getString(BOOKING_USER_FIRSTNAME));
                    bookingUser.setLastName(rs.getString(BOOKING_USER_LASTNAME));
                    bookingUser.setEmail(rs.getString(BOOKING_USER_EMAIL));
                    bookingUser.setNif(rs.getString(BOOKING_USER_NIF));
                    bookingUser.setPhone(rs.getString(BOOKING_USER_PHONE));
                    bookingUser.setActive(rs.getBoolean(BOOKING_USER_ACTIVE));
                    SecureSetter.setAttribute(bookingUser, "setAdmin", Boolean.class,
                        rs.getBoolean(BOOKING_USER_ADMIN));

                    schedule.addBooking(bookingUser);
                }
                hasNext = rs.next();
            } while (hasNext && rs.getInt(ALIAS_SCHEDULE_ID) == schedule.getId());

            return schedule;

        }

    }
}
