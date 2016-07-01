package busy.schedule;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADMIN;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_CATEGORY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COMPANY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.ALIAS_ROLE_ID;
import static busy.util.SQLUtil.ALIAS_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_NAME;
import static busy.util.SQLUtil.ALIAS_TIME_SLOT_ID;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.BOOKINGS_PER_ROLE;
import static busy.util.SQLUtil.BOOKING_USER_ACTIVE;
import static busy.util.SQLUtil.BOOKING_USER_ADMIN;
import static busy.util.SQLUtil.BOOKING_USER_EMAIL;
import static busy.util.SQLUtil.BOOKING_USER_FIRSTNAME;
import static busy.util.SQLUtil.BOOKING_USER_ID;
import static busy.util.SQLUtil.BOOKING_USER_LASTNAME;
import static busy.util.SQLUtil.BOOKING_USER_NIF;
import static busy.util.SQLUtil.BOOKING_USER_PHONE;
import static busy.util.SQLUtil.BUSINESS_NAME;
import static busy.util.SQLUtil.CIF;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.CREATE_DATE;
import static busy.util.SQLUtil.DESCRIPTION;
import static busy.util.SQLUtil.DURATION;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.IS_MANAGER;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.REPETITION_TYPE;
import static busy.util.SQLUtil.SCHEDULE_QUERY;
import static busy.util.SQLUtil.SERVICE_ID;
import static busy.util.SQLUtil.START_TIME;
import static busy.util.SQLUtil.TABLE_TIME_SLOT;
import static busy.util.SQLUtil.TRADE_NAME;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.company.Category;
import busy.company.Company;
import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.role.Role;
import busy.user.User;
import busy.util.SecureSetter;

@Repository
public class TimeSlotDaoImpl implements TimeSlotDao {

    private static final String SQL_UPDATE =
        "UPDATE " + TABLE_TIME_SLOT + " SET " + START_TIME + "= ?," + SERVICE_ID + "= ?" + " WHERE " + ID + "= ?";

    private static final String SQL_SELECT_BY_ID = SCHEDULE_QUERY + " WHERE " + ALIAS_TIME_SLOT_ID + "=?";

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
                timeSlot.setId(key.intValue());
            }

        }
    }

    @Override
    public TimeSlot findById(int id) {

        try {

            return jdbcTemplate.query(SQL_SELECT_BY_ID, new TimeSlotExtractor(), id);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    private class TimeSlotExtractor implements ResultSetExtractor<TimeSlot> {

        private Service service;

        @Override
        public TimeSlot extractData(ResultSet rs) throws SQLException {

            boolean hasNext = rs.next();

            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setId(rs.getInt(ALIAS_TIME_SLOT_ID));

            if (service == null) {

                service = new Service();
                service.setId(rs.getInt(ALIAS_SERVICE_ID));
                service.setRepetition(rs.getInt(REPETITION_TYPE));

                ServiceType serviceType = new ServiceType();
                serviceType.setId(rs.getInt(ALIAS_SERVICE_TYPE_ID));
                serviceType.setName(rs.getString(ALIAS_SERVICE_TYPE_NAME));
                serviceType.setDescription(rs.getString(DESCRIPTION));
                serviceType.setMaxBookingsPerRole(rs.getInt(BOOKINGS_PER_ROLE));
                serviceType.setDuration(rs.getInt(DURATION));

                Company company = new Company();
                company.setId(rs.getInt(ALIAS_COMPANY_ID));
                company.setTradeName(rs.getString(TRADE_NAME));
                company.setBusinessName(rs.getString(BUSINESS_NAME));
                company.setEmail(rs.getString(EMAIL));
                company.setCif(rs.getString(CIF));
                SecureSetter.setAttribute(company, "setActive", Boolean.class, rs.getBoolean(ACTIVE));
                DateTime createDate = new DateTime(rs.getTimestamp(CREATE_DATE));
                company.setCreateDate(createDate);

                Integer categoryId = 0;
                if ((categoryId = rs.getInt(ALIAS_CATEGORY_ID)) > 0) {

                    Category category = new Category();
                    category.setId(categoryId);
                    category.setName(rs.getString(NAME));

                    company.setCategory(category);
                }

                serviceType.setCompany(company);

                service.setServiceType(serviceType);
            }

            timeSlot.setService(service);

            timeSlot.setStartTime(new DateTime(rs.getTimestamp(START_TIME)));

            do {
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

                timeSlot.addSchedule(schedule);
            } while (hasNext && rs.getInt(ALIAS_TIME_SLOT_ID) == timeSlot.getId());

            return timeSlot;
        }

    }
}
