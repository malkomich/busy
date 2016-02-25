package busy.role;

import static busy.util.SQLUtil.ACTIVITY;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COMPANY_EMAIL;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.BRANCHID;
import static busy.util.SQLUtil.BUSINESS_NAME;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.CREATE_DATE;
import static busy.util.SQLUtil.DEFAULT;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.IS_MANAGER;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.TABLE_ROLE;
import static busy.util.SQLUtil.TRADE_NAME;
import static busy.util.SQLUtil.USERID;
import static busy.util.SQLUtil.ZIPCODE;

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

import busy.company.Branch;
import busy.company.Company;
import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.util.SecureSetter;

@Repository
public class RoleDaoImpl implements RoleDao {

	private static final String SQL_SELECT_BY_USERID = "SELECT * FROM " + TABLE_ROLE + " WHERE " + USERID
			+ "=? ORDER BY " + CREATE_DATE + " DESC";
	
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

	@Override
	public List<Role> findByUserId(int userId) {

		try {

			return jdbcTemplate.query(SQL_SELECT_BY_USERID, new RoleRowMapper(), userId);

		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}
	
	private class RoleRowMapper implements RowMapper<Role> {

		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {

			Role role = new Role();
			SecureSetter.setId(role, rs.getInt(ALIAS_USER_ID));
			
//			Branch branch = new Branch();
//			
//			// Set Address
//			Address address = new Address();
//			int addressId = rs.getInt(ALIAS_ADDR_ID);
//			SecureSetter.setId(address, addressId);
//			address.setAddress1(rs.getString(ADDR1));
//			address.setAddress2(rs.getString(ADDR2));
//			address.setZipCode(rs.getString(ZIPCODE));
//
//			City city = new City();
//			SecureSetter.setId(city, rs.getInt(ALIAS_CITY_ID));
//			city.setName(rs.getString(ALIAS_CITY_NAME));
//
//			Country country = new Country();
//			SecureSetter.setId(country, rs.getInt(ALIAS_COUNTRY_ID));
//			country.setName(rs.getString(ALIAS_COUNTRY_NAME));
//			country.setCode(rs.getString(CODE));
//
//			city.setCountry(country);
//
//			address.setCity(city);
//			branch.setAddress(address);
//			
//			// Set Company
//			Company company = new Company();
//			company.setTradeName(rs.getString(TRADE_NAME));
//			company.setBusinessName(rs.getString(BUSINESS_NAME));
//			company.setEmail(rs.getString(rs.getString(ALIAS_COMPANY_EMAIL)));
//			branch.setCompany(company);
//			
//			branch.setPhone(rs.getString(PHONE));
//			
//			role.setBranch(branch);
//			SecureSetter.setAttribute(role, "setManager", Boolean.class, rs.getBoolean(IS_MANAGER));
//			role.setActivity(rs.getString(ACTIVITY));

			return role;
		}
	}

}
