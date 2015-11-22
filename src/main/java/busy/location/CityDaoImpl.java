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
public class CityDaoImpl implements CityDao {

	private static final String SQL_SELECT_ALL = "SELECT city.id AS cityId, city.name AS cityName, "
			+ "country.id AS countryId, country.name AS countryName, country.code FROM city "
			+ "LEFT JOIN country ON city.country_id = country.id";

	private static final String SQL_INSERT = "INSERT INTO city (name, country_id) VALUES (?, ?)";

	private static final String SQL_UPDATE = "UPDATE city SET name = ?, country_id = ? WHERE id = ?";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<City> findAll() {

		return jdbcTemplate.query(SQL_SELECT_ALL, new CityRowMapper());

	}

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
			SecureSetter.setId(city, rs.getInt("cityId"));
			city.setName(rs.getString("cityName"));

			Country country = new Country();
			SecureSetter.setId(country, rs.getInt("countryId"));
			country.setName(rs.getString("countryName"));
			country.setCode(rs.getString("code"));

			city.setCountry(country);

			return city;
		}

	}

}