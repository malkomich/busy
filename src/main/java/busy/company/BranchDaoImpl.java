package busy.company;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ADDR1;
import static busy.util.SQLUtil.ADDR2;
import static busy.util.SQLUtil.ADDRID;
import static busy.util.SQLUtil.ALIAS_ADDR_ID;
import static busy.util.SQLUtil.ALIAS_BRANCH_ID;
import static busy.util.SQLUtil.ALIAS_CATEGORY_ID;
import static busy.util.SQLUtil.ALIAS_CATEGORY_NAME;
import static busy.util.SQLUtil.ALIAS_CITY_ID;
import static busy.util.SQLUtil.ALIAS_CITY_NAME;
import static busy.util.SQLUtil.ALIAS_COMPANY_EMAIL;
import static busy.util.SQLUtil.ALIAS_COMPANY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_ID;
import static busy.util.SQLUtil.ALIAS_COUNTRY_NAME;
import static busy.util.SQLUtil.BRANCH_SELECT_QUERY;
import static busy.util.SQLUtil.BUSINESS_NAME;
import static busy.util.SQLUtil.CIF;
import static busy.util.SQLUtil.CODE;
import static busy.util.SQLUtil.COMPANYID;
import static busy.util.SQLUtil.CREATE_DATE;
import static busy.util.SQLUtil.DEFAULT;
import static busy.util.SQLUtil.HEADQUARTERS;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.PHONE;
import static busy.util.SQLUtil.TABLE_BRANCH;
import static busy.util.SQLUtil.TRADE_NAME;
import static busy.util.SQLUtil.ZIPCODE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.location.Address;
import busy.location.City;
import busy.location.Country;
import busy.util.SecureSetter;

/**
 * Branch persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class BranchDaoImpl implements BranchDao {

    private static final String SQL_SELECT_BY_ID = BRANCH_SELECT_QUERY + " WHERE " + TABLE_BRANCH + "." + ID + "=?";

    private static final String SQL_SELECT_BY_COMPANY = BRANCH_SELECT_QUERY + " WHERE " + ALIAS_COMPANY_ID + "=?";

    private static final String SQL_SELECT_HEADQUARTERS = SQL_SELECT_BY_COMPANY + " AND " + HEADQUARTERS + "='true'";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_BRANCH + " SET " + COMPANYID + "= ?," + ADDRID + "= ?,"
        + HEADQUARTERS + "= ?, " + PHONE + "= ? " + "WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_BRANCH);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(COMPANYID, ADDRID, HEADQUARTERS, PHONE));
    }

    /*
     * (non-Javadoc)
     * @see busy.company.BranchDao#save(busy.company.Branch)
     */
    @Override
    public void save(Branch branch) {

        if (branch.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, branch.getCompanyId(), branch.getAddressId(), branch.isHeadquarters(),
                branch.getPhone(), branch.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(COMPANYID, branch.getCompanyId());
            parameters.put(ADDRID, branch.getAddressId());
            parameters.put(HEADQUARTERS, (branch.isHeadquarters() != null) ? branch.isHeadquarters() : DEFAULT);
            parameters.put(PHONE, branch.getPhone());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                branch.setId(key.intValue());
            }
        }
    }

    @Override
    public Branch findById(int id) {

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new BranchRowMapper(), id);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.company.BranchDao#findByCompany(busy.company.Company)
     */
    @Override
    public List<Branch> findByCompanyId(int companyId) {

        return jdbcTemplate.query(SQL_SELECT_BY_COMPANY, new BranchRowMapper(), companyId);
    }

    /*
     * (non-Javadoc)
     * @see busy.company.BranchDao#findHeadQuarters(busy.company.Company)
     */
    @Override
    public Branch findHeadQuarters(Company company) {

        if (company == null) {
            throw new IllegalArgumentException("The company cannot be null");
        }

        BranchRowMapper rowMapper = new BranchRowMapper();
        rowMapper.setCompany(company);

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_HEADQUARTERS, new BranchRowMapper(), company.getId());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class BranchRowMapper implements RowMapper<Branch> {

        private Company company;

        public void setCompany(Company company) {
            this.company = company;
        }

        @Override
        public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {

            Branch branch = new Branch();
            branch.setId(rs.getInt(ALIAS_BRANCH_ID));

            // Parse company
            if (company == null) {
                company = new Company();
                company.setId(rs.getInt(ALIAS_COMPANY_ID));
                company.setTradeName(rs.getString(TRADE_NAME));
                company.setBusinessName(rs.getString(BUSINESS_NAME));
                company.setEmail(rs.getString(ALIAS_COMPANY_EMAIL));
                company.setCif(rs.getString(CIF));
                SecureSetter.setAttribute(company, "setActive", Boolean.class, rs.getBoolean(ACTIVE));
                DateTime createDate = new DateTime(rs.getTimestamp(CREATE_DATE));
                company.setCreateDate(createDate);

                Integer categoryId = 0;
                if ((categoryId = rs.getInt(ALIAS_CATEGORY_ID)) > 0) {

                    Category category = new Category();
                    category.setId(categoryId);
                    category.setName(rs.getString(ALIAS_CATEGORY_NAME));

                    company.setCategory(category);
                }
            }

            branch.setCompany(company);

            // Parse address
            Address address = new Address();
            address.setId(rs.getInt(ALIAS_ADDR_ID));
            address.setAddress1(rs.getString(ADDR1));
            address.setAddress2(rs.getString(ADDR2));
            address.setZipCode(rs.getString(ZIPCODE));

            City city = new City();
            city.setId(rs.getInt(ALIAS_CITY_ID));
            city.setName(rs.getString(ALIAS_CITY_NAME));

            Country country = new Country();
            country.setId(rs.getInt(ALIAS_COUNTRY_ID));
            country.setName(rs.getString(ALIAS_COUNTRY_NAME));
            country.setCode(rs.getString(CODE));

            city.setCountry(country);

            address.setCity(city);

            branch.setAddress(address);

            // Parse main attribute and phone
            SecureSetter.setAttribute(branch, "setHeadquarters", Boolean.class, rs.getBoolean(HEADQUARTERS));
            branch.setPhone(rs.getString(PHONE));

            return branch;
        }

    }

}
