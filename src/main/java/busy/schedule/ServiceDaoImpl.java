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
import static busy.util.SQLUtil.ALIAS_SERVICE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_NAME;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.BOOKINGS_PER_ROLE;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.DESCRIPTION;
import static busy.util.SQLUtil.DURATION;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PASSWORD;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.ROLE_ID;
import static busy.util.SQLUtil.SERVICE_QUERY;
import static busy.util.SQLUtil.SERVICE_TYPE_ID;
import static busy.util.SQLUtil.START_DATETIME;
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
            SERVICE_QUERY + " WHERE ? <= " + START_DATETIME + " AND ? >= " + START_DATETIME + " AND " + ROLE_ID + "=?";

    private static final String SQL_SERVICE_TYPE_FILTER = " AND " + SERVICE_TYPE_ID + "=?";

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
        // TODO Auto-generated method stub

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

            Map<Integer, Service> items = new HashMap<>();

            while (rs.next()) {

                Service service = items.get(rs.getInt(ALIAS_SERVICE_ID));

                if (service == null) {

                    service = new Service();
                    int id = rs.getInt(ALIAS_SERVICE_ID);
                    SecureSetter.setId(service, id);
                    service.setRole(role);

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

                    items.put(id, service);
                }

                Integer userId = rs.getInt(ALIAS_USER_ID);
                if (userId != INVALID_ID) {
                    User user = new User();
                    SecureSetter.setId(user, userId);
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

                    service.addBooking(user);
                }
            }

            return new ArrayList<Service>(items.values());
        }

    }

}
