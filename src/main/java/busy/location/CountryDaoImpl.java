package busy.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.util.SecureSetter;

import static busy.util.SQLUtil.*;

@Repository
public class CountryDaoImpl implements CountryDao {

	private static final String SQL_SELECT_ALL = "SELECT " + ID + "," + NAME + "," + CODE + " FROM " + TABLE_COUNTRY;

	private static final String SQL_INSERT = "INSERT INTO " + TABLE_COUNTRY + "(" + NAME + "," + CODE + ") VALUES (?, ?)";

	private static final String SQL_UPDATE = "UPDATE " + TABLE_COUNTRY + " SET " + NAME + "= ?," + CODE + "= ? WHERE " + ID + "= ?";

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName(TABLE_COUNTRY);
		jdbcInsert.setGeneratedKeyName(ID);
		jdbcInsert.setColumnNames(Arrays.asList(NAME, CODE));

	}

	/* (non-Javadoc)
	 * @see busy.location.CountryDao#findAll()
	 */
	@Override
	public List<Country> findAll() {

		return jdbcTemplate.query(SQL_SELECT_ALL, new CountryRowMapper());

	}

	/* (non-Javadoc)
	 * @see busy.location.CountryDao#save(busy.location.Country)
	 */
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
			SecureSetter.setId(country, rs.getInt(ID));
			country.setName(rs.getString(NAME));
			country.setCode(rs.getString(CODE));

			return country;
		}

	}

}