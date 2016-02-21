package busy.company;

import static busy.util.SQLUtil.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.util.SecureSetter;

@Repository
public class CategoryDaoImpl implements CategoryDao {

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

}
