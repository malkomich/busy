package busy.location;

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

import busy.util.SecureSetter;

import static busy.util.SQLUtil.*;

@Repository
public class AddressDaoImpl implements AddressDao {

	private static final String SQL_SELECT_ALL = "SELECT " + TABLE_ADDRESS + "." + ID + " AS " + ALIAS_ADDRID + ","
			+ ADDR1 + "," + ADDR2 + "," + ZIPCODE + ", cityJoin.* FROM " + TABLE_ADDRESS + " LEFT JOIN ( SELECT "
			+ TABLE_CITY + "." + ID + " AS " + ALIAS_CITYID + "," + TABLE_CITY + "." + NAME + " AS " + ALIAS_CITYNAME
			+ "," + TABLE_COUNTRY + "." + ID + " AS " + ALIAS_COUNTRYID + "," + TABLE_COUNTRY + "." + NAME + " AS "
			+ ALIAS_COUNTRYNAME + "," + CODE + " FROM " + TABLE_CITY + " LEFT JOIN " + TABLE_COUNTRY + " ON "
			+ TABLE_CITY + "." + COUNTRYID + "=" + TABLE_COUNTRY + "." + ID + ") AS cityJoin ON " + TABLE_ADDRESS + "."
			+ CITYID + "= cityJoin." + ALIAS_CITYID;

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

	/* (non-Javadoc)
	 * @see busy.location.AddressDao#findAll()
	 */
	@Override
	public List<Address> findAll() {

		return jdbcTemplate.query(SQL_SELECT_ALL, new AddressRowMapper());

	}

	/* (non-Javadoc)
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
				SecureSetter.setId(address, key.intValue());
			}
		}

	}

	class AddressRowMapper implements RowMapper<Address> {

		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

			Address address = new Address();
			SecureSetter.setId(address, rs.getInt(ALIAS_ADDRID));
			address.setAddress1(rs.getString(ADDR1));
			address.setAddress2(rs.getString(ADDR2));
			address.setZipCode(rs.getString(ZIPCODE));

			City city = new City();
			SecureSetter.setId(city, rs.getInt(ALIAS_CITYID));
			city.setName(rs.getString(ALIAS_CITYNAME));

			Country country = new Country();
			SecureSetter.setId(country, rs.getInt(ALIAS_COUNTRYID));
			country.setName(rs.getString(ALIAS_COUNTRYNAME));
			country.setCode(rs.getString(CODE));

			city.setCountry(country);

			address.setCity(city);

			return address;
		}

	}

}
