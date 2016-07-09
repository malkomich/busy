package busy.notifications;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADMIN;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.ALIAS_NOTIFICATION_ID;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.CREATE_DATE;
import static busy.util.SQLUtil.DEFAULT;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.IS_READ;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.MESSAGE;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.NOTIFICATION_TYPE;
import static busy.util.SQLUtil.PASSWORD;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.TABLE_NOTIFICATION;
import static busy.util.SQLUtil.USERID;
import static busy.util.SQLUtil.USER_SELECT_QUERY;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.notifications.Notification.Type;
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Notification persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class NotificationDaoImpl implements NotificationDao {

    private static final String SQL_SELECT_BY_USERID = "SELECT " + TABLE_NOTIFICATION + "." + ID + " AS "
        + ALIAS_NOTIFICATION_ID + "," + NOTIFICATION_TYPE + "," + MESSAGE + "," + IS_READ + "," + CREATE_DATE + " FROM "
        + TABLE_NOTIFICATION + " WHERE " + USERID + "=? ORDER BY " + CREATE_DATE + " DESC";

    private static final String SQL_SELECT_BY_ID =
        "SELECT " + TABLE_NOTIFICATION + "." + ID + " AS " + ALIAS_NOTIFICATION_ID + "," + NOTIFICATION_TYPE + ","
            + MESSAGE + "," + IS_READ + "," + CREATE_DATE + ",userJoin.* FROM " + TABLE_NOTIFICATION + " LEFT JOIN ("
            + USER_SELECT_QUERY + ") as userJoin ON " + TABLE_NOTIFICATION + "." + USERID + "=userJoin." + ALIAS_USER_ID
            + " WHERE " + TABLE_NOTIFICATION + "." + ID + "=? ORDER BY " + CREATE_DATE + " DESC";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_NOTIFICATION + " SET " + USERID + "= ?,"
        + NOTIFICATION_TYPE + "=?," + MESSAGE + "=?," + IS_READ + "=?" + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_NOTIFICATION);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(USERID, NOTIFICATION_TYPE, MESSAGE, IS_READ));
    }

    /*
     * (non-Javadoc)
     * @see busy.notifications.NotificationDao#save(busy.notifications.Notification)
     */
    @Override
    public void save(Notification notification) {

        if (notification.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, notification.getUserId(), notification.getTypeCode(),
                notification.getMessageCode(), notification.isRead(), notification.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(USERID, notification.getUserId());
            parameters.put(NOTIFICATION_TYPE, notification.getTypeCode());
            parameters.put(MESSAGE, notification.getMessageCode());
            parameters.put(IS_READ, DEFAULT);

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                notification.setId(key.intValue());
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.notifications.NotificationDao#findByUserId(int)
     */
    @Override
    public List<Notification> findByUser(User user) {

        NotificationRowMapper rowMapper = new NotificationRowMapper();
        rowMapper.setUser(user);

        try {

            return jdbcTemplate.query(SQL_SELECT_BY_USERID, rowMapper, user.getId());

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    private class NotificationRowMapper implements RowMapper<Notification> {

        private User user;

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {

            Notification notification = new Notification();
            notification.setId(rs.getInt(ALIAS_NOTIFICATION_ID));
            Type type = Type.fromCode(rs.getString(NOTIFICATION_TYPE));
            notification.setType(type);
            SecureSetter.setAttribute(notification, "setMessageUnsecured", String.class, rs.getString(MESSAGE));
            notification.setRead(rs.getBoolean(IS_READ));
            DateTime createDate = new DateTime(rs.getTimestamp(CREATE_DATE));
            notification.setCreateDate(createDate);

            // Set User
            if (user == null) {
                user = new User();
                user.setId(rs.getInt(ALIAS_USER_ID));
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

                    user.setAddress(address);
                }
            }

            notification.setUser(user);

            return notification;
        }
    }

    public Notification findById(int id) {

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new NotificationRowMapper(), id);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

}
