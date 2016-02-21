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
public class CompanyDaoImpl implements CompanyDao {

	private static final String SQL_UPDATE = "UPDATE " + TABLE_COMPANY + " SET " + TRADE_NAME + "= ?," + BUSINESS_NAME
			+ "= ?," + EMAIL + "= ?, " + CIF + "= ?," + ACTIVE + "= ?," + CATEGORYID + "= ? " + "WHERE " + ID + "= ?";

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName(TABLE_COMPANY);
		jdbcInsert.setGeneratedKeyName(ID);
		jdbcInsert.setColumnNames(Arrays.asList(TRADE_NAME, BUSINESS_NAME, EMAIL, CIF, ACTIVE, CATEGORYID));
	}

	@Override
	public void save(Company company) {

		if (company.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, company.getTradeName(), company.getBusinessName(), company.getEmail(),
					company.getCif(), company.isActive(), company.getCategoryId(), company.getId());

		} else {

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(TRADE_NAME, company.getTradeName());
			parameters.put(BUSINESS_NAME, company.getBusinessName());
			parameters.put(EMAIL, company.getEmail());
			parameters.put(CIF, company.getCif());
			parameters.put(ACTIVE, company.isActive());
			parameters.put(CATEGORYID, company.getCategoryId());

			Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
			if (key != null) {
				SecureSetter.setId(company, key.intValue());
			}
		}
	}

}
