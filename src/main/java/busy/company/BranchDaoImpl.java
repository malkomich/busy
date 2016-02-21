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
public class BranchDaoImpl implements BranchDao {

	private static final String SQL_UPDATE = "UPDATE " + TABLE_BRANCH + " SET " + COMPANYID + "= ?," + ADDRID + "= ?,"
			+ HEADQUARTER + "= ?, " + PHONE + "= ? " + "WHERE " + ID + "= ?";
	
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName(TABLE_BRANCH);
		jdbcInsert.setGeneratedKeyName(ID);
		jdbcInsert
				.setColumnNames(Arrays.asList(COMPANYID, ADDRID, HEADQUARTER, PHONE));
	}
	
	@Override
	public void save(Branch branch) {

		if (branch.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, branch.getCompanyId(), branch.getAddressId(), branch.isHeadquarter(),
					branch.getPhone(), branch.getId());

		} else {

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(COMPANYID, branch.getCompanyId());
			parameters.put(ADDRID, branch.getAddressId());
			parameters.put(HEADQUARTER, branch.isHeadquarter());
			parameters.put(PHONE, branch.getPhone());

			Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
			if (key != null) {
				SecureSetter.setId(branch, key.intValue());
			}
		}
	}

}
