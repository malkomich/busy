package busy.company;

import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.NAME;
import static busy.util.SQLUtil.TABLE_CATEGORY;

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

import busy.util.SecureSetter;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	private static final String SQL_SELECT_ALL = "SELECT " + ID + "," + NAME + " FROM " + TABLE_CATEGORY;
	
	private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE " + TABLE_CATEGORY + "." + ID + "= ?";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE_CATEGORY + " SET " + NAME + "= ? WHERE " + ID + "= ?";

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName(TABLE_CATEGORY);
		jdbcInsert.setGeneratedKeyName(ID);
		jdbcInsert.setColumnNames(Arrays.asList(NAME));
	}

	/* (non-Javadoc)
	 * @see busy.company.CategoryDao#save(busy.company.Category)
	 */
	@Override
	public void save(Category category) {

		if (category.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, category.getName(), category.getId());

		} else {

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(NAME, category.getName());

			Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
			if (key != null) {
				SecureSetter.setId(category, key.intValue());
			}
		}
	}
	
	@Override
	public List<Category> findAll() {
		
		return jdbcTemplate.query(SQL_SELECT_ALL, new CategoryRowMapper());
	}

	/* (non-Javadoc)
	 * @see busy.company.CategoryDao#findById(int)
	 */
	@Override
	public Category findById(int categoryId) {

		try {

			return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new CategoryRowMapper(), categoryId);

		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}
	
	private class CategoryRowMapper implements RowMapper<Category> {

		@Override
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

			Category category = new Category();
			SecureSetter.setId(category, rs.getInt(ID));
			category.setName(rs.getString(NAME));

			return category;
		}

	}

}
