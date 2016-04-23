package busy.schedule;

import static busy.util.SQLUtil.ALIAS_COMPANY_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_NAME;
import static busy.util.SQLUtil.BOOKINGS_PER_ROLE;
import static busy.util.SQLUtil.COMPANYID;
import static busy.util.SQLUtil.COMPANY_SELECT_QUERY;
import static busy.util.SQLUtil.DESCRIPTION;
import static busy.util.SQLUtil.DURATION;
import static busy.util.SQLUtil.ID;
import static busy.util.SQLUtil.NAME;
import static busy.util.SQLUtil.TABLE_SERVICE_TYPE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import busy.company.Company;
import busy.util.OperationResult;
import busy.util.OperationResult.ResultCode;
import busy.util.SecureSetter;

/**
 * Service type persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ServiceTypeDaoImpl implements ServiceTypeDao {

    private static final String SQL_SELECT_ALL = "SELECT " + TABLE_SERVICE_TYPE + "." + ID + " AS "
            + ALIAS_SERVICE_TYPE_ID + "," + TABLE_SERVICE_TYPE + "." + NAME + " AS " + ALIAS_SERVICE_TYPE_NAME + ","
            + DESCRIPTION + "," + BOOKINGS_PER_ROLE + "," + DURATION + ",companyJoin.* FROM " + TABLE_SERVICE_TYPE
            + " LEFT JOIN (" + COMPANY_SELECT_QUERY + ") AS companyJoin ON " + TABLE_SERVICE_TYPE + "." + COMPANYID
            + "=companyJoin." + ALIAS_COMPANY_ID;

    private static final String SQL_SELECT_BY_COMPANY = SQL_SELECT_ALL + " WHERE " + COMPANYID + "=?";
    private static final String SQL_SELECT_BY_COMPANY_AND_NAME =
            SQL_SELECT_BY_COMPANY + " AND " + TABLE_SERVICE_TYPE + "." + NAME + "=?";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_SERVICE_TYPE + " SET " + NAME + "= ?," + DESCRIPTION
            + "= ?," + BOOKINGS_PER_ROLE + "= ?, " + DURATION + "= ?," + COMPANYID + "= ?" + " WHERE " + ID + "= ?";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_SERVICE_TYPE + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceTypeDao#save(busy.schedule.ServiceType)
     */
    @Override
    public ServiceType save(ServiceType serviceType) {

        if (serviceType.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, serviceType.getName(), serviceType.getDescription(),
                    serviceType.getMaxBookingsPerRole(), serviceType.getDuration(), serviceType.getCompanyId(),
                    serviceType.getId());
            return serviceType;

        } else {

            ServiceTypeRowMapper rowMapper = new ServiceTypeRowMapper();
            rowMapper.setCompany(serviceType.getCompany());

            Map<String, Object> parameters = new LinkedHashMap<String, Object>();
            parameters.put(NAME, serviceType.getName());
            parameters.put(DESCRIPTION, serviceType.getDescription());
            if (serviceType.getMaxBookingsPerRole() != null) {
                parameters.put(BOOKINGS_PER_ROLE, serviceType.getMaxBookingsPerRole());
            }
            if (serviceType.getDuration() != null) {
                parameters.put(DURATION, serviceType.getDuration());
            }
            parameters.put(COMPANYID, serviceType.getCompanyId());

            String query = generateInsertClause(parameters.keySet().toArray(new String[parameters.size()]));

            ServiceType sType = jdbcTemplate.queryForObject(query, parameters.values().toArray(), rowMapper);
            return sType;

        }
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceTypeDao#findByCompany(busy.company.Company)
     */
    @Override
    public List<ServiceType> findByCompany(Company company) {

        ServiceTypeRowMapper rowMapper = new ServiceTypeRowMapper();
        rowMapper.setCompany(company);

        return jdbcTemplate.query(SQL_SELECT_BY_COMPANY, rowMapper, company.getId());

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceTypeDao#findByNameAndCompany(java.lang.String,
     * busy.company.Company)
     */
    @Override
    public ServiceType findByCompanyAndName(Company company, String name) {

        ServiceTypeRowMapper rowMapper = new ServiceTypeRowMapper();
        rowMapper.setCompany(company);

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_BY_COMPANY_AND_NAME, rowMapper, company.getId(), name);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    private String generateInsertClause(String[] args) {

        String fieldNames = "";
        String valueMappers = "";

        for (int i = 0; i < args.length; i++) {
            fieldNames += args[i];
            valueMappers += "?";
            if (i < args.length - 1) {
                fieldNames += ",";
                valueMappers += ",";
            }
        }

        return "INSERT INTO " + TABLE_SERVICE_TYPE + "(" + fieldNames + ") VALUES(" + valueMappers
                + ") RETURNING " + ID + " AS " + ALIAS_SERVICE_TYPE_ID + "," + NAME + " AS " + ALIAS_SERVICE_TYPE_NAME
                + "," + DESCRIPTION + "," + BOOKINGS_PER_ROLE + "," + DURATION + "," + COMPANYID;
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceTypeDao#delete(busy.schedule.ServiceType)
     */
    @Override
    public OperationResult delete(ServiceType sType) {

        OperationResult result;
        try {
            int rowsAffected = jdbcTemplate.update(SQL_DELETE, sType.getId());
            result = (rowsAffected == 1) ? new OperationResult(ResultCode.OK)
                    : new OperationResult(ResultCode.NOT_EXISTS);
        } catch (DataIntegrityViolationException e) {
            result = new OperationResult(ResultCode.SERVICE_TYPE_WITH_BOOKINGS);
        }

        return result;
    }

    private class ServiceTypeRowMapper implements RowMapper<ServiceType> {

        private Company company;

        public void setCompany(Company company) {
            this.company = company;
        }

        @Override
        public ServiceType mapRow(ResultSet rs, int rowNum) throws SQLException {

            ServiceType serviceType = new ServiceType();
            SecureSetter.setId(serviceType, rs.getInt(ALIAS_SERVICE_TYPE_ID));
            serviceType.setName(rs.getString(ALIAS_SERVICE_TYPE_NAME));
            serviceType.setDescription(rs.getString(DESCRIPTION));
            serviceType.setMaxBookingsPerRole(rs.getInt(BOOKINGS_PER_ROLE));
            serviceType.setDuration(rs.getInt(DURATION));
            serviceType.setCompany(company);

            return serviceType;
        }

    }

}
