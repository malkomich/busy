package busy.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.util.SecureSetter;

import static busy.util.SQLUtil.*;

/**
 * City persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class CityDaoImpl implements CityDao {

    private static final String SQL_SELECT_ALL =
            "SELECT " + TABLE_CITY + "." + ID + " AS " + ALIAS_CITY_ID + "," + TABLE_CITY + "." + NAME + " AS "
                    + ALIAS_CITY_NAME + "," + TABLE_COUNTRY + "." + ID + " AS " + ALIAS_COUNTRY_ID + "," + TABLE_COUNTRY
                    + "." + NAME + " AS " + ALIAS_COUNTRY_NAME + "," + CODE + " FROM " + TABLE_CITY + " LEFT JOIN "
                    + TABLE_COUNTRY + " ON " + TABLE_CITY + "." + COUNTRYID + "=" + TABLE_COUNTRY + "." + ID;

    private static final String SQL_SELECT_BY_COUNTRY = SQL_SELECT_ALL + " WHERE " + CODE + "= ?";

    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE " + TABLE_CITY + "." + ID + "= ?";

    private static final String SQL_INSERT =
            "INSERT INTO " + TABLE_CITY + "(" + NAME + "," + COUNTRYID + ") VALUES (?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE " + TABLE_CITY + " SET " + NAME + "= ?," + COUNTRYID + "= ? WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_CITY);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(NAME, COUNTRYID));

    }

    /*
     * (non-Javadoc)
     * @see busy.location.CityDao#findAll()
     */
    @Override
    public List<City> findAll() {

        return jdbcTemplate.query(SQL_SELECT_ALL, new CityRowMapper());
    }

    /*
     * (non-Javadoc)
     * @see busy.location.CityDao#findByCountry(busy.location.Country)
     */
    @Override
    public List<City> findByCountryCode(String countryCode) {

        try {
            return jdbcTemplate.query(SQL_SELECT_BY_COUNTRY, new CityRowMapper(), countryCode);
        } catch (EmptyResultDataAccessException e) {

            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.location.CityDao#findById(int)
     */
    @Override
    public City findById(int cityId) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new CityRowMapper(), cityId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.location.CityDao#save(busy.location.City)
     */
    @Override
    public void save(City city) {

        if (city.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, city.getName(), city.getCountryId(), city.getId());
        } else {

            jdbcTemplate.update(SQL_INSERT, city.getName(), city.getCountryId());
        }

    }

    private class CityRowMapper implements RowMapper<City> {

        @Override
        public City mapRow(ResultSet rs, int rowNum) throws SQLException {

            City city = new City();
            SecureSetter.setId(city, rs.getInt(ALIAS_CITY_ID));
            city.setName(rs.getString(ALIAS_CITY_NAME));

            Country country = new Country();
            SecureSetter.setId(country, rs.getInt(ALIAS_COUNTRY_ID));
            country.setName(rs.getString(ALIAS_COUNTRY_NAME));
            country.setCode(rs.getString(CODE));

            city.setCountry(country);

            return city;
        }

    }

}
