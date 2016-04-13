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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import busy.company.Company;
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

    private static final String SQL_UPDATE = "UPDATE " + TABLE_SERVICE_TYPE + " SET " + NAME + "= ?," + DESCRIPTION
            + "= ?," + BOOKINGS_PER_ROLE + "= ?, " + DURATION + "= ?," + COMPANYID + "= ?" + " WHERE " + ID + "= ?";

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE_SERVICE_TYPE);
        jdbcInsert.setGeneratedKeyName(ID);
        jdbcInsert.setColumnNames(Arrays.asList(NAME, DESCRIPTION, BOOKINGS_PER_ROLE, DURATION, COMPANYID));
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceTypeDao#save(busy.schedule.ServiceType)
     */
    @Override
    public void save(ServiceType serviceType) {

        if (serviceType.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE, serviceType.getName(), serviceType.getDescription(),
                    serviceType.getMaxBookingsPerRole(), serviceType.getDuration(), serviceType.getCompanyId(),
                    serviceType.getId());

        } else {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(NAME, serviceType.getName());
            parameters.put(DESCRIPTION, serviceType.getDescription());
            parameters.put(BOOKINGS_PER_ROLE, serviceType.getMaxBookingsPerRole());
            parameters.put(DURATION, serviceType.getDuration());
            parameters.put(COMPANYID, serviceType.getCompanyId());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            if (key != null) {
                SecureSetter.setId(serviceType, key.intValue());
            }
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
