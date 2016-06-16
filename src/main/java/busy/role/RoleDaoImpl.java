package busy.role;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADMIN;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_BRANCH_ID;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COMPANY_EMAIL;
import static busy.util.SQLUtil.ALIAS_COMPANY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.ALIAS_ROLE_ID;
import static busy.util.SQLUtil.ALIAS_USER_ID;
import static busy.util.SQLUtil.BRANCHID;
import static busy.util.SQLUtil.BUSINESS_NAME;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.DEFAULT;
import static busy.util.SQLUtil.EMAIL;
import static busy.util.SQLUtil.FIRSTNAME;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.IS_MANAGER;
import static busy.util.SQLUtil.LASTNAME;
import static busy.util.SQLUtil.NIF;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.ROLE_SELECT_QUERY;
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
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Role persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    private static final String SQL_SELECT_BY_USERID = ROLE_SELECT_QUERY + " WHERE " + TABLE_ROLE + "." + USERID + "=?";

    private static final String SQL_SELECT_MANAGER_BY_COMPANY_ID =
        ROLE_SELECT_QUERY + " WHERE " + IS_MANAGER + "=true AND " + ALIAS_COMPANY_ID + "=?";

    private static final String SQL_SELECT_BY_ID = ROLE_SELECT_QUERY + " WHERE ";

    private static final String SQL_SELECT_BY_BRANCH =
        ROLE_SELECT_QUERY + " WHERE " + TABLE_ROLE + "." + BRANCHID + "=?";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_ROLE + " SET " + USERID + "= ?," + BRANCHID + "= ?,"
        + IS_MANAGER + "= ?" + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_ROLE);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(USERID, BRANCHID, IS_MANAGER));
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleDao#save(busy.role.Role)
     */
    @Override
    public void save(Role role) {

        if (role.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, role.getUserId(), role.getBranchId(), role.isManager(), role.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(USERID, role.getUserId());
            parameters.put(BRANCHID, role.getBranchId());
            parameters.put(IS_MANAGER, (role.isManager() != null) ? role.isManager() : DEFAULT);

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                SecureSetter.setId(role, key.intValue());
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleDao#findByUser(busy.user.User)
     */
    @Override
    public List<Role> findByUser(User user) {

        try {

            RoleRowMapper rowMapper = new RoleRowMapper();
            rowMapper.setUser(user);
            return jdbcTemplate.query(SQL_SELECT_BY_USERID, rowMapper, user.getId());

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleDao#findManagerByCompany(busy.company.Company)
     */
    @Override
    public Role findManagerByCompany(Company company) {

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_MANAGER_BY_COMPANY_ID, new RoleRowMapper(), company.getId());

        } catch (EmptyResultDataAccessException e) {

            return null;

        }
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleDao#findById(int)
     */
    @Override
    public List<Role> findById(Integer... ids) {

        String query = SQL_SELECT_BY_ID;
        for (int i = 0; i < ids.length; i++) {
            query += TABLE_ROLE + "." + ID + "=?";
            if (i != (ids.length - 1)) {
                query += " OR ";
            }
        }

        try {

            return jdbcTemplate.query(query, new RoleRowMapper(), (Object[]) ids);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.role.RoleDao#findByBranch(busy.company.Branch)
     */
    @Override
    public List<Role> findByBranch(Branch branch) {

        RoleRowMapper rowMapper = new RoleRowMapper();
        rowMapper.setBranch(branch);

        return jdbcTemplate.query(SQL_SELECT_BY_BRANCH, rowMapper, branch.getId());
    }

    private class RoleRowMapper implements RowMapper<Role> {

        private User user;
        private Branch branch;

        public void setUser(User user) {
            this.user = user;
        }

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {

            Role role = new Role();
            SecureSetter.setId(role, rs.getInt(ALIAS_ROLE_ID));

            // Set User
            User user = this.user;
            if (user == null) {
                user = new User();
                SecureSetter.setId(user, rs.getInt(ALIAS_USER_ID));
                user.setFirstName(rs.getString(FIRSTNAME));
                user.setLastName(rs.getString(LASTNAME));
                user.setEmail(rs.getString(EMAIL));
                user.setNif(rs.getString(NIF));
                user.setPhone(rs.getString(PHONE));
                user.setActive(rs.getBoolean(ACTIVE));
                SecureSetter.setAttribute(user, "setAdmin", Boolean.class, rs.getBoolean(ADMIN));

                Integer addressId = 0;
                if ((addressId = rs.getInt(ALIAS_ADDR_ID)) > 0) {

                    Address address = new Address();

                    SecureSetter.setId(address, addressId);
                    address.setAddress1(rs.getString(ADDR1));
                    address.setAddress2(rs.getString(ADDR2));
                    address.setZipCode(rs.getString(ZIPCODE));

                    City city = new City();
                    SecureSetter.setId(city, rs.getInt(ALIAS_CITY_ID));
                    city.setName(rs.getString(ALIAS_CITY_NAME));

                    Country country = new Country();
                    SecureSetter.setId(country, rs.getInt(ALIAS_COUNTRY_ID));
                    country.setName(rs.getString(ALIAS_COUNTRY_NAME));
                    country.setCode(rs.getString(CODE));

                    city.setCountry(country);

                    address.setCity(city);

                    user.setAddress(address);
                }
            }

            role.setUser(user);

            Branch branch = this.branch;
            if (branch == null) {
                branch = new Branch();
                SecureSetter.setId(branch, rs.getInt(ALIAS_BRANCH_ID));

                // Set Address
                Address address = new Address();
                int addressId = rs.getInt(ALIAS_ADDR_ID);
                SecureSetter.setId(address, addressId);
                address.setAddress1(rs.getString(ADDR1));
                address.setAddress2(rs.getString(ADDR2));
                address.setZipCode(rs.getString(ZIPCODE));

                City city = new City();
                SecureSetter.setId(city, rs.getInt(ALIAS_CITY_ID));
                city.setName(rs.getString(ALIAS_CITY_NAME));

                Country country = new Country();
                SecureSetter.setId(country, rs.getInt(ALIAS_COUNTRY_ID));
                country.setName(rs.getString(ALIAS_COUNTRY_NAME));
                country.setCode(rs.getString(CODE));

                city.setCountry(country);

                address.setCity(city);
                branch.setAddress(address);

                // Set Company
                Company company = new Company();
                SecureSetter.setId(company, rs.getInt(ALIAS_COMPANY_ID));
                company.setTradeName(rs.getString(TRADE_NAME));
                company.setBusinessName(rs.getString(BUSINESS_NAME));
                company.setEmail(rs.getString(ALIAS_COMPANY_EMAIL));
                branch.setCompany(company);

                branch.setPhone(rs.getString(PHONE));
            }

            role.setBranch(branch);

            SecureSetter.setAttribute(role, "setManager", Boolean.class, rs.getBoolean(IS_MANAGER));

            return role;
        }

    }

}
