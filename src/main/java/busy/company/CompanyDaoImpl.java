package busy.company;

import static busy.util.SQLUtil.ACTIVE;
import static busy.util.SQLUtil.ALIAS_CATEGORY_ID;
import static busy.util.SQLUtil.ALIAS_COMPANY_ID;
import static busy.util.SQLUtil.BUSINESS_NAME;
import static busy.util.SQLUtil.CATEGORYID;
import static busy.util.SQLUtil.CIF;
import static busy.util.SQLUtil.CREATE_DATE;
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

import busy.util.SecureSetter;

/**
 * Company persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

    private static final String SQL_SELECT = "SELECT " + TABLE_COMPANY + "." + ID + " AS " + ALIAS_COMPANY_ID + ","
        + TRADE_NAME + "," + BUSINESS_NAME + "," + EMAIL + "," + CIF + "," + CREATE_DATE + "," + ACTIVE + ","
        + TABLE_CATEGORY + "." + ID + " AS " + ALIAS_CATEGORY_ID + "," + NAME + " FROM " + TABLE_COMPANY + " LEFT JOIN "
        + TABLE_CATEGORY + " ON " + TABLE_COMPANY + "." + CATEGORYID + "=" + TABLE_CATEGORY + "." + ID;

    private static final String SQL_SELECT_ALL = SQL_SELECT + " ORDER BY " + CREATE_DATE;

    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " WHERE " + TABLE_COMPANY + "." + ID + "= ?";

    private static final String SQL_SELECT_BY_BUSINESS_NAME =
        SQL_SELECT + " WHERE " + TABLE_COMPANY + "." + BUSINESS_NAME + "= ?";

    private static final String SQL_SELECT_BY_EMAIL = SQL_SELECT + " WHERE " + TABLE_COMPANY + "." + EMAIL + "= ?";

    private static final String SQL_SELECT_BY_CIF = SQL_SELECT + " WHERE " + TABLE_COMPANY + "." + CIF + "= ?";

    private static final String SQL_SELECT_ACTIVE_BY_PARTIAL_NAME =
        SQL_SELECT + " WHERE " + ACTIVE + "=true AND ( UPPER(" + TABLE_COMPANY + "." + TRADE_NAME
            + ") LIKE UPPER(?) OR UPPER(" + TABLE_COMPANY + "." + BUSINESS_NAME + ") LIKE UPPER(?))";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_COMPANY + " SET " + TRADE_NAME + "= ?," + BUSINESS_NAME
        + "= ?," + EMAIL + "= ?, " + CIF + "= ?," + ACTIVE + "= ?," + CATEGORYID + "= ? " + "WHERE " + ID + "= ?";

    private static final String SQL_COUNT_ALL = "SELECT COUNT(*) FROM (" + SQL_SELECT + ") AS countTable";

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

    /*
     * (non-Javadoc)
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
                company.setId(key.intValue());
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyDao#findAll()
     */
    @Override
    public List<Company> findAll() {

        return jdbcTemplate.query(SQL_SELECT_ALL, new CompanyRowMapper());
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyDao#findById(int)
     */
    @Override
    public Company findById(int id) {

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new CompanyRowMapper(), id);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    /*
     * (non-Javadoc)
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

    /*
     * (non-Javadoc)
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

    /*
     * (non-Javadoc)
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

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyDao#findByPartialName(java.lang.String)
     */
    @Override
    public List<Company> findActivesByPartialName(String partialName) {

        return jdbcTemplate.query(SQL_SELECT_ACTIVE_BY_PARTIAL_NAME, new CompanyRowMapper(), "%" + partialName + "%",
            "%" + partialName + "%");
    }

    /*
     * (non-Javadoc)
     * @see busy.company.CompanyDao#countAll()
     */
    @Override
    public int countAll() {

        return jdbcTemplate.queryForObject(SQL_COUNT_ALL, Integer.class);
    }

    /**
     * Checks if a Company object exists in the database.
     * 
     * @param company
     *            the Company object to check
     * @return True when the company exists, False when it does not.
     */
    public boolean exists(Company company) {

        return findById(company.getId()) != null;
    }

    private class CompanyRowMapper implements RowMapper<Company> {

        @Override
        public Company mapRow(ResultSet rs, int rowNum) throws SQLException {

            Company company = new Company();
            company.setId(rs.getInt(ALIAS_COMPANY_ID));
            company.setTradeName(rs.getString(TRADE_NAME));
            company.setBusinessName(rs.getString(BUSINESS_NAME));
            company.setEmail(rs.getString(EMAIL));
            company.setCif(rs.getString(CIF));
            SecureSetter.setAttribute(company, "setActive", Boolean.class, rs.getBoolean(ACTIVE));
            DateTime createDate = new DateTime(rs.getTimestamp(CREATE_DATE));
            company.setCreateDate(createDate);

            Integer categoryId = 0;
            if ((categoryId = rs.getInt(ALIAS_CATEGORY_ID)) > 0) {

                Category category = new Category();
                category.setId(categoryId);
                category.setName(rs.getString(NAME));

                company.setCategory(category);
            }

            return company;
        }

    }

}
