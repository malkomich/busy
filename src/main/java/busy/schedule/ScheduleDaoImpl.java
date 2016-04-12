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
import static busy.util.SQLUtil.ALIAS_DAY_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_HOUR_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_NAME;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.ALIAS_WEEK_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_YEAR_SCHEDULE_ID;
import static busy.util.SQLUtil.BOOKINGS_PER_ROLE;
import static busy.util.SQLUtil.BRANCHID;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.DAY_OF_WEEK;
import static busy.util.SQLUtil.DESCRIPTION;
import static busy.util.SQLUtil.DURATION;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.END_TIME;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.IS_DEFAULT;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PASSWORD;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.START_TIME;
import static busy.util.SQLUtil.WEEK_OF_YEAR;
import static busy.util.SQLUtil.YEAR;
import static busy.util.SQLUtil.YEAR_SCHEDULE_QUERY;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private static final String SQL_SELECT_YEAR_FROM_BRANCH =
            YEAR_SCHEDULE_QUERY + " WHERE " + BRANCHID + "=? AND " + YEAR + "=?";

    private static final String SQL_SELECT_WEEKS_OF_YEAR =
            YEAR_SCHEDULE_QUERY + " WHERE " + BRANCHID + "=? AND " + YEAR + "=?";

    private static final String SQL_SELECT_DEFAULT_WEEK =
            YEAR_SCHEDULE_QUERY + " WHERE " + IS_DEFAULT + "= 't' AND " + BRANCHID + "=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findByBranchAndYear(busy.company.Branch, int)
     */
    @Override
    public YearSchedule findYearFromBranch(Branch branch, int year) {

        YearScheduleRowMapper rowMapper = new YearScheduleRowMapper();
        rowMapper.setBranch(branch);

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_YEAR_FROM_BRANCH, rowMapper, branch.getId(), year);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findByYearAndWeeks(int, int[])
     */
    @Override
    public List<WeekSchedule> findWeeksFromBranch(Branch branch, int year, int... weeks) {

        int numOfWeeks = weeks.length;

        String query = (numOfWeeks > 0) ? SQL_SELECT_WEEKS_OF_YEAR + " AND (" : SQL_SELECT_WEEKS_OF_YEAR;
        for (int i = 0; i < numOfWeeks; i++) {
            query += WEEK_OF_YEAR + "=?" + ((i < numOfWeeks - 1) ? " OR " : ")");
        }

        Object[] params = new Object[numOfWeeks + 2];
        params[0] = branch.getId();
        params[1] = year;
        for (int i = 0; i < numOfWeeks; i++) {
            params[i + 2] = weeks[i];
        }

        WeekScheduleRowMapper rowMapper = new WeekScheduleRowMapper();
        rowMapper.setBranch(branch);

        try {

            return jdbcTemplate.queryForObject(query, rowMapper, params);

        } catch (EmptyResultDataAccessException e) {

            return new ArrayList<WeekSchedule>();
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findDefaultWeek()
     */
    @Override
    public WeekSchedule findDefaultWeek(Branch branch) {

        WeekScheduleRowMapper rowMapper = new WeekScheduleRowMapper();
        rowMapper.setBranch(branch);
        
        try {

            List<WeekSchedule> weekScheduleList =
                    jdbcTemplate.queryForObject(SQL_SELECT_DEFAULT_WEEK, rowMapper, branch.getId());

            if (weekScheduleList != null) {
                return weekScheduleList.get(0);
            }

        } catch (EmptyResultDataAccessException e) {
            // Nothing to do, null will be returned
        }
        return null;
    }

    private class YearScheduleRowMapper implements RowMapper<YearSchedule> {

        private Branch branch;

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        @Override
        public YearSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {

            boolean hasNext = true;

            // Parse year schedule
            YearSchedule yearSchedule = null;

            do {
                if (yearSchedule == null) {
                    yearSchedule = new YearSchedule();
                    SecureSetter.setId(yearSchedule, rs.getInt(ALIAS_YEAR_SCHEDULE_ID));
                    yearSchedule.setBranch(branch);
                    yearSchedule.setYear(rs.getInt(YEAR));
                }

                // Parse week schedules
                WeekSchedule weekSchedule = null;

                do {
                    if (weekSchedule == null) {
                        weekSchedule = new WeekSchedule();
                        SecureSetter.setId(weekSchedule, rs.getInt(ALIAS_WEEK_SCHEDULE_ID));
                        weekSchedule.setWeekOfYear(rs.getInt(WEEK_OF_YEAR));
                        weekSchedule.setDefault(rs.getBoolean(IS_DEFAULT));
                        weekSchedule.setYearSchedule(yearSchedule);
                    }

                    // Parse day schedules
                    DaySchedule daySchedule = null;

                    do {
                        if (daySchedule == null) {
                            daySchedule = new DaySchedule();
                            SecureSetter.setId(daySchedule, rs.getInt(ALIAS_DAY_SCHEDULE_ID));
                            daySchedule.setDayOfWeek(rs.getInt(DAY_OF_WEEK));
                            daySchedule.setWeekSchedule(weekSchedule);
                        }

                        // Parse hour schedules
                        HourSchedule hourSchedule = null;

                        do {
                            if (hourSchedule == null) {
                                hourSchedule = new HourSchedule();
                                SecureSetter.setId(hourSchedule, rs.getInt(ALIAS_HOUR_SCHEDULE_ID));
                                hourSchedule.setStartTime(new LocalTime(rs.getTime(START_TIME)));
                                hourSchedule.setEndTime(new LocalTime(rs.getTime(END_TIME)));
                                hourSchedule.setDaySchedule(daySchedule);
                            }

                            // Parse services
                            Service service = new Service();
                            SecureSetter.setId(service, rs.getInt(ALIAS_SERVICE_ID));
                            service.setHourSchedule(hourSchedule);

                            ServiceType serviceType = new ServiceType();
                            SecureSetter.setId(serviceType, rs.getInt(ALIAS_SERVICE_TYPE_ID));
                            serviceType.setCompany(branch.getCompany());
                            serviceType.setName(rs.getString(ALIAS_SERVICE_TYPE_NAME));
                            serviceType.setDescription(rs.getString(DESCRIPTION));
                            serviceType.setMaxBookingsPerRole(rs.getInt(BOOKINGS_PER_ROLE));
                            serviceType.setDuration(rs.getInt(DURATION));

                            service.setServiceType(serviceType);

                            Role role = new Role();
                            role.setBranch(branch);

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

                            role.setUser(user);

                            service.setRole(role);

                            hourSchedule.addService(service);

                            hasNext = rs.next();

                        } while (hasNext && rs.getInt(ALIAS_HOUR_SCHEDULE_ID) == hourSchedule.getId());

                        daySchedule.addHourSchedule(hourSchedule);

                    } while (hasNext && rs.getInt(ALIAS_DAY_SCHEDULE_ID) == daySchedule.getId());

                    weekSchedule.addDaySchedule(daySchedule);

                } while (hasNext && rs.getInt(ALIAS_WEEK_SCHEDULE_ID) == weekSchedule.getId());

                yearSchedule.addWeekSchedule(weekSchedule);

            } while (hasNext);

            return yearSchedule;
        }

    }

    private class WeekScheduleRowMapper implements RowMapper<List<WeekSchedule>> {

        private Branch branch;

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        @Override
        public List<WeekSchedule> mapRow(ResultSet rs, int rowNum) throws SQLException {

            boolean hasNext = true;

            List<WeekSchedule> weekScheduleList = new ArrayList<WeekSchedule>();

            YearSchedule yearSchedule = new YearSchedule();
            SecureSetter.setId(yearSchedule, rs.getInt(ALIAS_YEAR_SCHEDULE_ID));
            yearSchedule.setBranch(branch);
            yearSchedule.setYear(rs.getInt(YEAR));

            do {
                // Parse week schedules
                WeekSchedule weekSchedule = null;

                do {
                    if (weekSchedule == null) {
                        weekSchedule = new WeekSchedule();
                        SecureSetter.setId(weekSchedule, rs.getInt(ALIAS_WEEK_SCHEDULE_ID));
                        weekSchedule.setWeekOfYear(rs.getInt(WEEK_OF_YEAR));
                        weekSchedule.setDefault(rs.getBoolean(IS_DEFAULT));
                        weekSchedule.setYearSchedule(yearSchedule);
                    }

                    // Parse day schedules
                    DaySchedule daySchedule = null;

                    do {
                        if (daySchedule == null) {
                            daySchedule = new DaySchedule();
                            SecureSetter.setId(daySchedule, rs.getInt(ALIAS_DAY_SCHEDULE_ID));
                            daySchedule.setDayOfWeek(rs.getInt(DAY_OF_WEEK));
                            daySchedule.setWeekSchedule(weekSchedule);
                        }

                        // Parse hour schedules
                        HourSchedule hourSchedule = null;

                        do {
                            if (hourSchedule == null) {
                                hourSchedule = new HourSchedule();
                                SecureSetter.setId(hourSchedule, rs.getInt(ALIAS_HOUR_SCHEDULE_ID));
                                hourSchedule.setStartTime(new LocalTime(rs.getTime(START_TIME)));
                                hourSchedule.setEndTime(new LocalTime(rs.getTime(END_TIME)));
                                hourSchedule.setDaySchedule(daySchedule);
                            }

                            // Parse services
                            Service service = new Service();
                            SecureSetter.setId(service, rs.getInt(ALIAS_SERVICE_ID));
                            service.setHourSchedule(hourSchedule);

                            ServiceType serviceType = new ServiceType();
                            SecureSetter.setId(serviceType, rs.getInt(ALIAS_SERVICE_TYPE_ID));
                            serviceType.setCompany(branch.getCompany());
                            serviceType.setName(rs.getString(ALIAS_SERVICE_TYPE_NAME));
                            serviceType.setDescription(rs.getString(DESCRIPTION));
                            serviceType.setMaxBookingsPerRole(rs.getInt(BOOKINGS_PER_ROLE));
                            serviceType.setDuration(rs.getInt(DURATION));

                            service.setServiceType(serviceType);

                            Role role = new Role();
                            role.setBranch(branch);

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

                            role.setUser(user);

                            service.setRole(role);

                            hourSchedule.addService(service);

                            hasNext = rs.next();

                        } while (hasNext && rs.getInt(ALIAS_HOUR_SCHEDULE_ID) == hourSchedule.getId());

                        daySchedule.addHourSchedule(hourSchedule);

                    } while (hasNext && rs.getInt(ALIAS_DAY_SCHEDULE_ID) == daySchedule.getId());

                    weekSchedule.addDaySchedule(daySchedule);

                } while (hasNext && rs.getInt(ALIAS_WEEK_SCHEDULE_ID) == weekSchedule.getId());

                weekScheduleList.add(weekSchedule);

            } while (hasNext);

            return weekScheduleList;
        }
    }

}
