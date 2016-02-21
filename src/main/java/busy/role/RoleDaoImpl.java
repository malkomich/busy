package busy.role;

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
public class RoleDaoImpl implements RoleDao {

	private static final String SQL_UPDATE = "UPDATE " + TABLE_ROLE + " SET " + USERID + "= ?," + BRANCHID + "= ?,"
			+ IS_MANAGER + "= ?, " + ACTIVITY + "= ? " + "WHERE " + ID + "= ?";
	
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName(TABLE_ROLE);
		jdbcInsert.setGeneratedKeyName(ID);
		jdbcInsert
				.setColumnNames(Arrays.asList(USERID, BRANCHID, IS_MANAGER, ACTIVITY));
	}
	
	@Override
	public void save(Role role) {

		if (role.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, role.getUserId(), role.getBranchId(), role.isManager(),
					role.getActivity(), role.getId());

		} else {

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(USERID, role.getUserId());
			parameters.put(BRANCHID, role.getBranchId());
			parameters.put(IS_MANAGER, (role.isManager() != null) ? role.isManager() : DEFAULT);
			parameters.put(ACTIVITY, role.getActivity());

			Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
			if (key != null) {
				SecureSetter.setId(role, key.intValue());
			}
		}
	}

}
