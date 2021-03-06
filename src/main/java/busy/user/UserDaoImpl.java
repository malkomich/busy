package busy.user;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADDRESS_SELECT_QUERY;
import static busy.util.SQLUtil.ADDRID;
import static busy.util.SQLUtil.ADMIN;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.DEFAULT;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PASSWORD;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.TABLE_USER;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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
import busy.util.SecureSetter;

/**
 * User persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final String SQL_SELECT_ALL = "SELECT " + TABLE_USER + "." + ID + " AS " + ALIAS_USER_ID + ","
        + FIRSTNAME + "," + LASTNAME + "," + EMAIL + "," + PASSWORD + "," + NIF + "," + PHONE + "," + ACTIVE + ","
        + ADMIN + ", addressJoin.* FROM " + TABLE_USER + " LEFT JOIN (" + ADDRESS_SELECT_QUERY + ") AS addressJoin ON "
        + TABLE_USER + "." + ADDRID + "= addressJoin." + ALIAS_ADDR_ID;

    private static final String SQL_SELECT_BY_EMAIL = SQL_SELECT_ALL + " WHERE " + EMAIL + "= ?";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_USER + " SET " + FIRSTNAME + "= ?," + LASTNAME + "= ?,"
        + EMAIL + "= ?, " + PASSWORD + "= ?," + NIF + "= ?," + PHONE + "= ?," + ACTIVE + "= ?," + ADMIN + "= ?,"
        + ADDRID + "= ? " + "WHERE " + ID + "= ?";

    private static final String SQL_UPDATE_ACTIVE_STATUS =
        "UPDATE " + TABLE_USER + " SET " + ACTIVE + "= ? " + "WHERE " + ID + "= ?";

    private static final String SQL_COUNT_ALL = "SELECT COUNT(*) FROM (" + SQL_SELECT_ALL + ") AS countTable";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_USER);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert
            .setColumnNames(Arrays.asList(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, NIF, PHONE, ACTIVE, ADMIN, ADDRID));
    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserDao#findAll()
     */
    @Override
    public List<User> findAll() {

        return jdbcTemplate.query(SQL_SELECT_ALL, new UserRowMapper());

    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserDao#findByEmail(java.lang.String)
     */
    @Override
    public User findByEmail(String email) {

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new UserRowMapper(), email);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }

    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserDao#save(busy.user.User)
     */
    @Override
    public void save(User user) {

        if (user.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPassword(), user.getNif(), user.getPhone(), user.isActive(), user.isAdmin(),
                user.getAddressId(), user.getId());
        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(FIRSTNAME, user.getFirstName());
            parameters.put(LASTNAME, user.getLastName());
            parameters.put(EMAIL, user.getEmail());
            parameters.put(PASSWORD, user.getPassword());
            parameters.put(NIF, user.getNif());
            parameters.put(PHONE, user.getPhone());
            parameters.put(ACTIVE, DEFAULT);
            parameters.put(ADMIN, DEFAULT);
            parameters.put(ADDRID, user.getAddressId());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                user.setId(key.intValue());
            }
        }

    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserDao#changeActiveStatus(java.lang.Integer, boolean)
     */
    @Override
    public int changeActiveStatus(Integer userId, boolean active) {

        return jdbcTemplate.update(SQL_UPDATE_ACTIVE_STATUS, active, userId);
    }

    /*
     * (non-Javadoc)
     * @see busy.user.UserDao#countAll()
     */
    @Override
    public int countAll() {

        return jdbcTemplate.queryForObject(SQL_COUNT_ALL, Integer.class);
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            User user = new User();
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

            return user;
        }

    }

}
