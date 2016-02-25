package busy.user;

import static busy.util.SQLUtil.*;

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

	private static final String SQL_SELECT_ALL = "SELECT " + TABLE_USER + "." + ID + " AS " + ALIAS_USER_ID + ","
			+ FIRSTNAME + "," + LASTNAME + "," + EMAIL + "," + PASSWORD + "," + NIF + "," + PHONE + "," + ACTIVE + ","
			+ ADMIN + ", addressJoin.* FROM " + TABLE_USER + " LEFT JOIN (" + ADDRESS_SELECT_QUERY + ") AS addressJoin ON " + TABLE_USER
			+ "." + ADDRID + "= addressJoin." + ALIAS_ADDR_ID;

	private static final String SQL_SELECT_BY_EMAIL = SQL_SELECT_ALL + " WHERE " + EMAIL + "= ?";

	private static final String SQL_UPDATE = "UPDATE " + TABLE_USER + " SET " + FIRSTNAME + "= ?," + LASTNAME + "= ?,"
			+ EMAIL + "= ?, " + PASSWORD + "= ?," + NIF + "= ?," + PHONE + "= ?," + ACTIVE + "= ?," + ADMIN + "= ?,"
			+ ADDRID + "= ? " + "WHERE " + ID + "= ?";

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
				SecureSetter.setId(user, key.intValue());
			}
		}

	}

	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {

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

			return user;
		}

	}

}