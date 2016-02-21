package busy.company;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ALIAS_CATEGORYID;
import static busy.util.SQLUtil.ALIAS_COMPANYID;
import static busy.util.SQLUtil.BUSINESS_NAME;
import static busy.util.SQLUtil.CATEGORYID;
import static busy.util.SQLUtil.CIF;
import static busy.util.SQLUtil.DEFAULT;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.NAME;
import static busy.util.SQLUtil.TABLE_CATEGORY;
import static busy.util.SQLUtil.TABLE_COMPANY;
import static busy.util.SQLUtil.TRADE_NAME;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
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
public class CompanyDaoImpl implements CompanyDao {

	private static final String SQL_SELECT_ALL = "SELECT " + TABLE_COMPANY + "." + ID + " AS " + ALIAS_COMPANYID + ","
			+ TRADE_NAME + "," + BUSINESS_NAME + "," + EMAIL + "," + CIF + "," + ACTIVE + "," + TABLE_CATEGORY + "."
			+ ID + " AS " + ALIAS_CATEGORYID + "," + NAME + " FROM " + TABLE_COMPANY + " LEFT JOIN " + TABLE_CATEGORY
			+ " ON " + TABLE_COMPANY + "." + CATEGORYID + "=" + TABLE_CATEGORY + "." + ID;

	private static final String SQL_SELECT_BY_BUSINESS_NAME = SQL_SELECT_ALL + " WHERE " + TABLE_COMPANY + "."
			+ BUSINESS_NAME + "= ?";

	private static final String SQL_SELECT_BY_EMAIL = SQL_SELECT_ALL + " WHERE " + TABLE_COMPANY + "." + EMAIL + "= ?";

	private static final String SQL_SELECT_BY_CIF = SQL_SELECT_ALL + " WHERE " + TABLE_COMPANY + "." + CIF + "= ?";

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

	/* (non-Javadoc)
	 * @see busy.company.CompanyDao#save(busy.company.Company)
	 */
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
			parameters.put(ACTIVE, DEFAULT);
			parameters.put(CATEGORYID, company.getCategoryId());

			Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
			if (key != null) {
				SecureSetter.setId(company, key.intValue());
			}
		}
	}

	/* (non-Javadoc)
	 * @see busy.company.CompanyDao#findByBusinessName(java.lang.String)
	 */
	@Override
	public Company findByBusinessName(String businessName) {

		try {

			return jdbcTemplate.queryForObject(SQL_SELECT_BY_BUSINESS_NAME, new CompanyRowMapper(), businessName);

		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	/* (non-Javadoc)
	 * @see busy.company.CompanyDao#findByEmail(java.lang.String)
	 */
	@Override
	public Company findByEmail(String email) {

		try {

			return jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new CompanyRowMapper(), email);

		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	/* (non-Javadoc)
	 * @see busy.company.CompanyDao#findByCif(java.lang.String)
	 */
	@Override
	public Company findByCif(String cif) {

		try {

			return jdbcTemplate.queryForObject(SQL_SELECT_BY_CIF, new CompanyRowMapper(), cif);

		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	private class CompanyRowMapper implements RowMapper<Company> {

		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {

			Company company = new Company();
			SecureSetter.setId(company, rs.getInt(ALIAS_COMPANYID));
			company.setTradeName(rs.getString(TRADE_NAME));
			company.setBusinessName(rs.getString(BUSINESS_NAME));
			company.setEmail(rs.getString(EMAIL));
			company.setCif(rs.getString(CIF));

			Integer categoryId = 0;
			if ((categoryId = rs.getInt(ALIAS_CATEGORYID)) > 0) {

				Category category = new Category();
				SecureSetter.setId(category, categoryId);
				category.setName(rs.getString(NAME));

				company.setCategory(category);
			}

			return company;
		}

	}

}
