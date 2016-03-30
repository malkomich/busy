package busy.booking;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADMIN;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.ALIAS_DAY_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_HOUR_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.ALIAS_WEEK_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_YEAR_SCHEDULE_ID;
import static busy.util.SQLUtil.BRANCHID;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.DAY_OF_WEEK;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.END_TIME;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.HOUR_SCHEDULE_ID;
import static busy.util.SQLUtil.IS_DEFAULT;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PASSWORD;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.START_TIME;
import static busy.util.SQLUtil.TABLE_BOOKING;
import static busy.util.SQLUtil.USERID;
import static busy.util.SQLUtil.USER_SELECT_QUERY;
import static busy.util.SQLUtil.WEEK_OF_YEAR;
import static busy.util.SQLUtil.YEAR;
import static busy.util.SQLUtil.YEAR_SCHEDULE_QUERY;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import busy.company.Branch;
import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.schedule.DaySchedule;
import busy.schedule.HourSchedule;
import busy.schedule.WeekSchedule;
import busy.schedule.YearSchedule;
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Booking persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class BookingDaoImpl implements BookingDao {

    private static final String SQL_SELECT_BY_BRANCH_AND_YEAR =
            "SELECT userJoin.*, yearScheduleJoin.* FROM " + TABLE_BOOKING + " LEFT JOIN (" + USER_SELECT_QUERY
                    + ") as userJoin ON " + TABLE_BOOKING + "." + USERID + "=userJoin." + ALIAS_USER_ID + " LEFT JOIN ("
                    + YEAR_SCHEDULE_QUERY + ") as yearScheduleJoin ON " + TABLE_BOOKING + "." + HOUR_SCHEDULE_ID
                    + "=yearScheduleJoin." + ALIAS_HOUR_SCHEDULE_ID + " WHERE " + BRANCHID + "=? AND " + YEAR + "=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /*
     * (non-Javadoc)
     * @see busy.booking.BookingDao#findByUserAndWeeks(busy.user.User, int[])
     */
    @Override
    public List<Booking> findByBranchAndYearAndWeeks(Branch branch, int year, int... weeks) {

        int numOfWeeks = weeks.length;

        String query = (numOfWeeks > 0) ? SQL_SELECT_BY_BRANCH_AND_YEAR + " AND (" : SQL_SELECT_BY_BRANCH_AND_YEAR;
        for (int i = 0; i < numOfWeeks; i++) {
            query += WEEK_OF_YEAR + "=?" + ((i < weeks.length - 1) ? " OR " : ")");
        }

        Object[] params = new Object[numOfWeeks + 2];
        params[0] = branch.getId();
        params[1] = year;
        for (int i = 0; i < numOfWeeks; i++) {
            params[i + 2] = weeks[i];
        }

        BookingRowMapper rowMapper = new BookingRowMapper();
        rowMapper.setBranch(branch);

        try {

            return jdbcTemplate.query(query, rowMapper, params);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    private class BookingRowMapper implements RowMapper<Booking> {

        private Branch branch;

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {

            Booking booking = new Booking();

            // Parse user
            User user = new User();
            SecureSetter.setId(user, rs.getInt(ALIAS_USER_ID));
            user.setFirstName(rs.getString(FIRSTNAME));
            user.setLastName(rs.getString(LASTNAME));
            user.setEmail(rs.getString(EMAIL));
            user.setPassword(rs.getString(PASSWORD));
            user.setNif(rs.getString(NIF));
            user.setPhone(rs.getString(PHONE));
            user.setActive(rs.getBoolean(ACTIVE));
            SecureSetter.setAttribute(user, "setAdmin", Boolean.class, rs.getBoolean(ADMIN));

            Integer addressId = 0;
            if ((addressId = rs.getInt(ALIAS_ADDR_ID)) > 0) {

                Address address = new Address();

                SecureSetter.setId(address, addressId);
                address.setAddress1(rs.getString(ADDR1));
                address.setAddress2(rs.getString(ADDR2));
                address.setZipCode(rs.getString(ZIPCODE));

                City city = new City();
                SecureSetter.setId(city, rs.getInt(ALIAS_CITY_ID));
                city.setName(rs.getString(ALIAS_CITY_NAME));

                Country country = new Country();
                SecureSetter.setId(country, rs.getInt(ALIAS_COUNTRY_ID));
                country.setName(rs.getString(ALIAS_COUNTRY_NAME));
                country.setCode(rs.getString(CODE));

                city.setCountry(country);

                address.setCity(city);

                user.setAddress(address);
            }

            booking.setUser(user);

            // Parse Schedule
            HourSchedule hourSchedule = new HourSchedule();
            SecureSetter.setId(hourSchedule, rs.getInt(ALIAS_HOUR_SCHEDULE_ID));
            hourSchedule.setStartTime(new LocalTime(rs.getTime(START_TIME)));
            hourSchedule.setEndTime(new LocalTime(rs.getTime(END_TIME)));

            DaySchedule daySchedule = new DaySchedule();
            SecureSetter.setId(daySchedule, rs.getInt(ALIAS_DAY_SCHEDULE_ID));
            daySchedule.setDayOfWeek(rs.getInt(DAY_OF_WEEK));

            WeekSchedule weekSchedule = new WeekSchedule();
            SecureSetter.setId(weekSchedule, rs.getInt(ALIAS_WEEK_SCHEDULE_ID));
            weekSchedule.setWeekOfYear(rs.getInt(WEEK_OF_YEAR));
            weekSchedule.setDefault(rs.getBoolean(IS_DEFAULT));

            YearSchedule yearSchedule = new YearSchedule();
            SecureSetter.setId(yearSchedule, rs.getInt(ALIAS_YEAR_SCHEDULE_ID));
            yearSchedule.setBranch(branch);
            yearSchedule.setYear(rs.getInt(YEAR));

            weekSchedule.setYearSchedule(yearSchedule);

            daySchedule.setWeekSchedule(weekSchedule);

            hourSchedule.setDaySchedule(daySchedule);

            booking.setHourSchedule(hourSchedule);

            return booking;
        }

    }

}
