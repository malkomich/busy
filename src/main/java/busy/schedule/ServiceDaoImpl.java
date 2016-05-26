package busy.schedule;

import static busy.util.SQLUtil.ALIAS_ROLE_ID;
import static busy.util.SQLUtil.ALIAS_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_NAME;
import static busy.util.SQLUtil.BOOKINGS_PER_ROLE;
import static busy.util.SQLUtil.BOOKING_USER_ACTIVE;
import static busy.util.SQLUtil.BOOKING_USER_ADMIN;
import static busy.util.SQLUtil.BOOKING_USER_EMAIL;
import static busy.util.SQLUtil.BOOKING_USER_FIRSTNAME;
import static busy.util.SQLUtil.BOOKING_USER_ID;
import static busy.util.SQLUtil.BOOKING_USER_LASTNAME;
import static busy.util.SQLUtil.BOOKING_USER_NIF;
import static busy.util.SQLUtil.BOOKING_USER_PHONE;
import static busy.util.SQLUtil.CORRELATION;
import static busy.util.SQLUtil.DESCRIPTION;
import static busy.util.SQLUtil.DURATION;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.SCHEDULE_QUERY;
import static busy.util.SQLUtil.SERVICE_TYPE_ID;
import static busy.util.SQLUtil.START_DATETIME;
import static busy.util.SQLUtil.TABLE_SERVICE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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

    private static final String SQL_SELECT_BETWEEN_DAYS = SCHEDULE_QUERY + " WHERE ? <= " + START_DATETIME
            + " AND ? >= " + START_DATETIME + " AND " + ALIAS_ROLE_ID + "=?";

    private static final String SQL_SERVICE_TYPE_FILTER = " AND " + SERVICE_TYPE_ID + "=?";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_SERVICE + " SET " + START_DATETIME + "= ?,"
            + SERVICE_TYPE_ID + "= ?," + CORRELATION + "= ?" + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_SERVICE);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(START_DATETIME, SERVICE_TYPE_ID, CORRELATION));

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
        resultSetExtractor.setRole(role);

        String query = SQL_SELECT_BETWEEN_DAYS;
        List<Object> params = new ArrayList<>();
        params.add(new Timestamp(initDay.getMillis()));
        params.add(new Timestamp(endDay.getMillis()));
        
        params.add(role.getId());

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

            jdbcTemplate.update(SQL_UPDATE, service.getStartTimestamp(), service.getServiceTypeId(),
                    service.getCorrelation(), service.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(START_DATETIME, service.getStartTimestamp());
            parameters.put(SERVICE_TYPE_ID, service.getServiceTypeId());
            parameters.put(CORRELATION, service.getCorrelation());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                SecureSetter.setId(service, key.intValue());
            }

        }

    }

    private class ServiceSetExtractor implements ResultSetExtractor<List<Service>> {

        private Role role;
        private ServiceType serviceType;

        public void setRole(Role role) {
            this.role = role;
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
                    service.setCorrelation(rs.getInt(CORRELATION));

                    ServiceType serviceType = this.serviceType;
                    if (serviceType == null) {
                        serviceType = new ServiceType();
                        SecureSetter.setId(serviceType, rs.getInt(ALIAS_SERVICE_TYPE_ID));
                        serviceType.setName(rs.getString(ALIAS_SERVICE_TYPE_NAME));
                        serviceType.setDescription(rs.getString(DESCRIPTION));
                        serviceType.setMaxBookingsPerRole(rs.getInt(BOOKINGS_PER_ROLE));
                        serviceType.setDuration(rs.getInt(DURATION));
                        serviceType.setCompany(role.getCompany());
                    }
                    service.setServiceType(serviceType);

                    service.setStartTime(new DateTime(rs.getTimestamp(START_DATETIME)));

                    serviceMap.put(id, service);
                }

                Schedule schedule = new Schedule();
                SecureSetter.setId(schedule, rs.getInt(ALIAS_SCHEDULE_ID));
                schedule.setRole(role);

                do {
                    Integer userId = rs.getInt(BOOKING_USER_ID);
                    if (userId != INVALID_ID) {
                        User user = new User();
                        SecureSetter.setId(user, userId);
                        user.setFirstName(rs.getString(BOOKING_USER_FIRSTNAME));
                        user.setLastName(rs.getString(BOOKING_USER_LASTNAME));
                        user.setEmail(rs.getString(BOOKING_USER_EMAIL));
                        user.setNif(rs.getString(BOOKING_USER_NIF));
                        user.setPhone(rs.getString(BOOKING_USER_PHONE));
                        user.setActive(rs.getBoolean(BOOKING_USER_ACTIVE));
                        SecureSetter.setAttribute(user, "setAdmin", Boolean.class, rs.getBoolean(BOOKING_USER_ADMIN));

                        schedule.addBooking(user);
                    }
                    hasNext = rs.next();
                } while (hasNext && rs.getInt(ALIAS_SCHEDULE_ID) == schedule.getId());

                service.addSchedule(schedule);

            }

            return new ArrayList<Service>(serviceMap.values());
        }

    }

}
