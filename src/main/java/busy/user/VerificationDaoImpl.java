package busy.user;

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
import static busy.util.SQLUtil.TABLE_REGISTRY;
import static busy.util.SQLUtil.TOKEN;
import static busy.util.SQLUtil.USERID;
import static busy.util.SQLUtil.USER_SELECT_QUERY;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.util.SecureSetter;

/**
 * Verification persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class VerificationDaoImpl implements VerificationDao {

    private static final String SQL_SELECT_BY_TOKEN =
            "SELECT * FROM " + TABLE_REGISTRY + " LEFT JOIN (" + USER_SELECT_QUERY + ") AS userJoin ON "
                    + TABLE_REGISTRY + "." + USERID + "= userJoin." + ALIAS_USER_ID + " WHERE " + TOKEN + "= ?";

    private static final String SQL_DELETE_REGISTRY = "DELETE FROM " + TABLE_REGISTRY + " WHERE " + USERID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_REGISTRY);
        jdbcInsert.setColumnNames(Arrays.asList(USERID, TOKEN));
    }

    /*
     * (non-Javadoc)
     * @see busy.user.RegistryDao#save(int)
     */
    @Override
    public void save(int userId, String token) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(USERID, userId);
        parameters.put(TOKEN, token);

        jdbcInsert.execute(new MapSqlParameterSource(parameters));

    }

    /*
     * (non-Javadoc)
     * @see busy.user.RegistryDao#delete(int)
     */
    @Override
    public void deleteByUserId(int userId) {

        jdbcTemplate.update(SQL_DELETE_REGISTRY, userId);
    }

    /*
     * (non-Javadoc)
     * @see busy.user.VerificationDao#findByToken(java.lang.String)
     */
    @Override
    public Verification findByToken(String token) {

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_BY_TOKEN, new VerificationRowMapper(), token);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    private class VerificationRowMapper implements RowMapper<Verification> {

        @Override
        public Verification mapRow(ResultSet rs, int rowNum) throws SQLException {

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

            Verification verification = new Verification();
            verification.setUser(user);
            verification.setToken(rs.getString(TOKEN));

            return verification;
        }

    }

}
