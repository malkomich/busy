package busy.schedule;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADMIN;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_BRANCH_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
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
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.DESCRIPTION;
import static busy.util.SQLUtil.DURATION;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.IS_MANAGER;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.REPETITION_TYPE;
import static busy.util.SQLUtil.SCHEDULE_QUERY;
import static busy.util.SQLUtil.SERVICE_TYPE_ID;
import static busy.util.SQLUtil.START_TIME;
import static busy.util.SQLUtil.TABLE_SERVICE;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import busy.company.Branch;
import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.role.Role;
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Service persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ServiceDaoImpl implements ServiceDao {

    private static final String SQL_SELECT_BETWEEN_DAYS =
        SCHEDULE_QUERY + " WHERE (? <= " + START_TIME + " AND ? >= " + START_TIME + ") OR " + REPETITION_TYPE + "<> 0";

    private static final String SQL_ROLE_FILTER = " AND " + ALIAS_ROLE_ID + "=?";

    private static final String SQL_BRANCH_FILTER = " AND " + ALIAS_BRANCH_ID + "=?";

    private static final String SQL_SERVICE_TYPE_FILTER = " AND " + SERVICE_TYPE_ID + "=?";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_SERVICE + " SET " + SERVICE_TYPE_ID + "= ?,"
        + REPETITION_TYPE + "= ?" + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceDao#findBetweenDays(org.joda.time.DateTime, org.joda.time.DateTime,
     * busy.role.Role, busy.schedule.ServiceType)
     */
    @Override
    public List<Service> findBetweenDays(DateTime initDay, DateTime endDay, Role role, ServiceType serviceType) {

        if (initDay == null || endDay == null) {
            throw new IllegalArgumentException("The days between which find the services cannot be null");
        }

        if (role == null) {
            throw new IllegalArgumentException("The role cannot be null");
        }

        ServiceSetExtractor resultSetExtractor = new ServiceSetExtractor();
        resultSetExtractor.setBranch(role.getBranch());

        String query = SQL_SELECT_BETWEEN_DAYS;
        List<Object> params = new ArrayList<>();
        params.add(new Timestamp(initDay.getMillis()));
        params.add(new Timestamp(endDay.getMillis()));

        if (role.isManager()) {
            query += SQL_BRANCH_FILTER;
            params.add(role.getBranchId());
        } else {
            query += SQL_ROLE_FILTER;
            params.add(role.getId());
            resultSetExtractor.setRole(role);
        }

        if (serviceType != null) {
            query += SQL_SERVICE_TYPE_FILTER;
            params.add(serviceType.getId());
            resultSetExtractor.setServiceType(serviceType);
        }

        return jdbcTemplate.query(query, resultSetExtractor, params.toArray());
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceDao#save(busy.schedule.Service)
     */
    @Override
    public void save(Service service) {

        if (service.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, service.getServiceTypeId(), service.getRepetitionType(), service.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(SERVICE_TYPE_ID, service.getServiceTypeId());
            if (service.getRepetitionType() != null) {
                parameters.put(REPETITION_TYPE, service.getRepetitionType());
            }

            String query = generateInsertClause(parameters.keySet().toArray(new String[parameters.size()]));

            Integer id = jdbcTemplate.queryForObject(query, parameters.values().toArray(), Integer.class);
            if (id != null) {
                SecureSetter.setId(service, id.intValue());
            }
        }
    }

    private String generateInsertClause(String[] args) {

        String fieldNames = "";
        String valueMappers = "";

        for (int i = 0; i < args.length; i++) {
            fieldNames += args[i];
            valueMappers += "?";
            if (i < args.length - 1) {
                fieldNames += ",";
                valueMappers += ",";
            }
        }

        return "INSERT INTO " + TABLE_SERVICE + "(" + fieldNames + ") VALUES(" + valueMappers + ") RETURNING " + ID;
    }

    private class ServiceSetExtractor implements ResultSetExtractor<List<Service>> {

        private Role role;
        private Branch branch;
        private ServiceType serviceType;

        public void setRole(Role role) {
            this.role = role;
        }

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        public void setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
        }

        @Override
        public List<Service> extractData(ResultSet rs) throws SQLException, DataAccessException {

            boolean hasNext = rs.next();

            Map<Integer, Service> serviceMap = new HashMap<>();

            while (hasNext) {

                Service service = serviceMap.get(rs.getInt(ALIAS_SERVICE_ID));

                if (service == null) {

                    service = new Service();
                    int id = rs.getInt(ALIAS_SERVICE_ID);
                    SecureSetter.setId(service, id);
                    service.setRepetition(rs.getInt(REPETITION_TYPE));

                    ServiceType serviceType = this.serviceType;
                    if (serviceType == null) {
                        serviceType = new ServiceType();
                        SecureSetter.setId(serviceType, rs.getInt(ALIAS_SERVICE_TYPE_ID));
                        serviceType.setName(rs.getString(ALIAS_SERVICE_TYPE_NAME));
                        serviceType.setDescription(rs.getString(DESCRIPTION));
                        serviceType.setMaxBookingsPerRole(rs.getInt(BOOKINGS_PER_ROLE));
                        serviceType.setDuration(rs.getInt(DURATION));
                        serviceType.setCompany(branch.getCompany());
                    }
                    service.setServiceType(serviceType);

                    serviceMap.put(id, service);
                }

                TimeSlot timeSlot = new TimeSlot();
                SecureSetter.setId(timeSlot, rs.getInt(ALIAS_TIME_SLOT_ID));

                timeSlot.setService(service);
                timeSlot.setStartTime(new DateTime(rs.getTimestamp(START_TIME)));

                do {
                    Schedule schedule = new Schedule();
                    SecureSetter.setId(schedule, rs.getInt(ALIAS_SCHEDULE_ID));

                    Role role = this.role;
                    if (role == null) {
                        role = new Role();
                        SecureSetter.setId(role, rs.getInt(ALIAS_ROLE_ID));

                        // Set User
                        User roleUser = new User();
                        SecureSetter.setId(roleUser, rs.getInt(ALIAS_USER_ID));
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

                            roleUser.setAddress(address);
                        }

                        role.setUser(roleUser);

                        role.setBranch(branch);

                        SecureSetter.setAttribute(role, "setManager", Boolean.class, rs.getBoolean(IS_MANAGER));
                    }
                    schedule.setRole(role);

                    do {
                        Integer userId = rs.getInt(BOOKING_USER_ID);
                        if (userId != INVALID_ID) {
                            User bookingUser = new User();
                            SecureSetter.setId(bookingUser, userId);
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

                service.addTimeSlot(timeSlot);

            }

            return new ArrayList<Service>(serviceMap.values());
        }

    }

}
