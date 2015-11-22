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
public class CountryDaoImpl implements CountryDao {

	private static final String SQL_SELECT_ALL = "SELECT id, name, code FROM country";

	private static final String SQL_INSERT = "INSERT INTO country (name, code) VALUES (?, ?)";

	private static final String SQL_UPDATE = "UPDATE country SET name = ?, code = ? WHERE id = ?";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Country> findAll() {

		return jdbcTemplate.query(SQL_SELECT_ALL, new CountryRowMapper());

	}

	@Override
	public void save(Country country) {

		if (country.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, country.getName(), country.getCode(), country.getId());
		} else {

			jdbcTemplate.update(SQL_INSERT, country.getName(), country.getCode());
		}

	}

	private class CountryRowMapper implements RowMapper<Country> {

		@Override
		public Country mapRow(ResultSet rs, int rowNum) throws SQLException {

			Country country = new Country();
			SecureSetter.setId(country, rs.getInt("id"));
			country.setName(rs.getString("name"));
			country.setCode(rs.getString("code"));

			return country;
		}

	}

}