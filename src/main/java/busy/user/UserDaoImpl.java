package busy.user;

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

@Repository
public class UserDaoImpl implements UserDao {

	private static final String SQL_SELECT_ALL = "SELECT person.id AS userId, first_name, last_name, email, "
			+ "password, nif, phone, active, admin_role, addressJoin.* FROM person LEFT JOIN ("
			+ "SELECT address.id AS addressId, address1, address2, zip_code, cityJoin.* FROM address LEFT JOIN ("
			+ "SELECT city.id AS cityId, city.name AS cityName, country.id AS countryId, country.name "
			+ "AS countryName, code FROM city LEFT JOIN country ON city.country_id = country.id) AS cityJoin "
			+ "ON address.city_id = cityJoin.cityId) AS addressJoin ON person.address_id = addressJoin.addressId";

	private static final String SQL_SELECT_BY_EMAIL = SQL_SELECT_ALL + " WHERE email = ?";

	private static final String SQL_UPDATE = "UPDATE person SET first_name = ?, last_name = ?, email = ?, "
			+ "password = ?, nif = ?, phone = ?, active = ?, admin_role = ?, address_id = ? "
			+ "WHERE id = ?";

	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName("person");
		jdbcInsert.setGeneratedKeyName("id");
		jdbcInsert.setColumnNames(Arrays.asList("first_name", "last_name", "email", "password", "nif", "phone", "active", "admin_role", "address_id"));
	}

	@Override
	public List<User> findAll() {

		return jdbcTemplate.query(SQL_SELECT_ALL, new UserRowMapper());

	}

	@Override
	public User findByEmail(String email) {

		try {

			return jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new UserRowMapper(), email);

		} catch (EmptyResultDataAccessException e) {

			return null;
		}

	}

	@Override
	public void save(User user) {

		if (user.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, user.getFirstName(), user.getLastName(),
					user.getEmail(), user.getPassword(), user.getNif(), user.getPhone(),
					user.isActive(), user.isAdmin(), user.getAddressId(), user.getId());
		} else {
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("first_name", user.getFirstName());
			parameters.put("last_name", user.getLastName());
			parameters.put("email", user.getEmail());
			parameters.put("password", user.getPassword());
			parameters.put("nif", user.getNif());
			parameters.put("phone", user.getPhone());
			parameters.put("active", user.isActive());
			parameters.put("admin_role", user.isAdmin());
			parameters.put("address_id", user.getAddressId());
			
			Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
			if (key != null){
				SecureSetter.setId(user, key.intValue());
//				user.setId((int) key.longValue());
			}
		}

	}

	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {

			User user = new User();
			SecureSetter.setId(user, rs.getInt("userId"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setNif(rs.getString("nif"));
			user.setPhone(rs.getString("phone"));
			user.setActive(rs.getBoolean("active"));
			SecureSetter.setAttribute(user, "setAdmin", Boolean.class, rs.getBoolean("admin_role"));

			Integer addressId = 0;
			if ((addressId = rs.getInt("addressId")) > 0) {

				Address address = new Address();
				
				SecureSetter.setId(address, addressId);
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

				user.setAddress(address);
			}

			return user;
		}

	}

}