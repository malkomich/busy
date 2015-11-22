package busy.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import busy.util.SecureSetter;

@Repository
public class AddressDaoImpl implements AddressDao {

	private static final String SQL_SELECT_ALL = "SELECT address.id AS addressId, address1, address2, "
			+ "zip_code, cityJoin.* FROM address LEFT JOIN ("
			+ "SELECT city.id AS cityId, city.name AS cityName, country.id AS countryId, country.name "
			+ "AS countryName, code FROM city LEFT JOIN country ON city.country_id = country.id) "
			+ "AS cityJoin ON address.city_id = cityJoin.cityId";

	private static final String SQL_INSERT = "INSERT INTO address (address1, address2, zip_code, city_id) "
			+ "VALUES (?, ?, ?, ?)";

	private static final String SQL_UPDATE = "UPDATE address SET address1 = ?, address2 = ?, zip_code = ?, "
			+ "city_id = ? WHERE id = ?";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Address> findAll() {

		return jdbcTemplate.query(SQL_SELECT_ALL, new AddressRowMapper());

	}

	@Override
	public void save(Address address) {

		if (address.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, address.getAddress1(), address.getAddress2(),
					address.getZipCode(), address.getCityId(), address.getId());
		} else {

			jdbcTemplate.update(SQL_INSERT, address.getAddress1(), address.getAddress2(),
					address.getZipCode(), address.getCityId());
		}

	}

	class AddressRowMapper implements RowMapper<Address> {

		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

			Address address = new Address();
			SecureSetter.setId(address, rs.getInt("addressId"));
			address.setAddress1(rs.getString("address1"));
			address.setAddress2(rs.getString("address2"));
			address.setZipCode(rs.getString("zip_code"));

			City city = new City();
			SecureSetter.setId(city, rs.getInt("cityId"));
			city.setName(rs.getString("cityName"));

			Country country = new Country();
			SecureSetter.setId(country, rs.getInt("countryId"));
			country.setName(rs.getString("countryName"));
			country.setCode(rs.getString("code"));

			city.setCountry(country);

			address.setCity(city);

			return address;
		}

	}

}
