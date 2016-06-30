package busy.location;

import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADDRESS_SELECT_QUERY;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.CITYID;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.TABLE_ADDRESS;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * Address persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class AddressDaoImpl implements AddressDao {

    private static final String SQL_SELECT_ALL = ADDRESS_SELECT_QUERY;

    private static final String SQL_UPDATE = "UPDATE " + TABLE_ADDRESS + " SET " + ADDR1 + "= ?," + ADDR2 + "= ?,"
            + ZIPCODE + "= ?, " + CITYID + "= ? WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_ADDRESS);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(ADDR1, ADDR2, ZIPCODE, CITYID));
    }

    /*
     * (non-Javadoc)
     * @see busy.location.AddressDao#findAll()
     */
    @Override
    public List<Address> findAll() {

        return jdbcTemplate.query(SQL_SELECT_ALL, new AddressRowMapper());

    }

    /*
     * (non-Javadoc)
     * @see busy.location.AddressDao#save(busy.location.Address)
     */
    @Override
    public void save(Address address) {

        if (address.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, address.getAddress1(), address.getAddress2(), address.getZipCode(),
                    address.getCityId(), address.getId());
        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(ADDR1, address.getAddress1());
            parameters.put(ADDR2, address.getAddress2());
            parameters.put(ZIPCODE, address.getZipCode());
            parameters.put(CITYID, address.getCityId());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                address.setId(key.intValue());
            }
        }

    }

    class AddressRowMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

            Address address = new Address();
            address.setId(rs.getInt(ALIAS_ADDR_ID));
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

            return address;
        }

    }

}
