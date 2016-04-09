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
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PASSWORD;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.SERVICE_ID;
import static busy.util.SQLUtil.TABLE_BOOKING;
import static busy.util.SQLUtil.USERID;
import static busy.util.SQLUtil.USER_SELECT_QUERY;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.service.Service;
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

    private static final String SQL_SELECT_BY_SERVICES = "SELECT " + TABLE_BOOKING + "." + SERVICE_ID
            + ", userJoin.* FROM " + TABLE_BOOKING + " LEFT JOIN (" + USER_SELECT_QUERY + ") as userJoin ON "
            + TABLE_BOOKING + "." + USERID + "=userJoin." + ALIAS_USER_ID + " WHERE (";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /*
     * (non-Javadoc)
     * @see busy.booking.BookingDao#findByServices(java.util.List)
     */
    @Override
    public Map<Service, List<Booking>> findByServices(List<Service> serviceList) {

        Map<Service, List<Booking>> resultMap = new HashMap<Service, List<Booking>>();
        for (Service service : serviceList) {
            resultMap.put(service, new ArrayList<Booking>());
        }

        // Generate the SQL query
        int numOfServices = serviceList.size();
        String query = SQL_SELECT_BY_SERVICES;

        for (int i = 0; i < numOfServices; i++) {
            query += SERVICE_ID + "=?" + ((i < numOfServices - 1) ? " OR " : ")");
        }

        Object[] params = new Object[numOfServices];
        for (int i = 0; i < numOfServices; i++) {
            params[i] = serviceList.get(i).getId();
        }

        // Execute the query
        List<Booking> bookingList = new ArrayList<Booking>();
        if (!serviceList.isEmpty()) {
            try {

                bookingList = jdbcTemplate.query(query, new BookingRowMapper(), params);

            } catch (EmptyResultDataAccessException e) {
                // Nothing to do here, the list just keeps empty
            }
        }

        // Parse the results to the map
        for (Booking booking : bookingList) {

            Service service = serviceList.stream().filter(item -> item.getId() == booking.getServiceId())
                    .collect(Collectors.toList()).get(0);

            resultMap.get(service).add(booking);
        }

        return resultMap;

    }

    private class BookingRowMapper implements RowMapper<Booking> {

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

            // Parse Service id
            booking.setServiceId(rs.getInt(SERVICE_ID));

            return booking;
        }

    }

}
