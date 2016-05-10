package busy.schedule;

import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_ID;
import static busy.util.SQLUtil.ALIAS_SERVICE_TYPE_NAME;
import static busy.util.SQLUtil.BOOKINGS_PER_ROLE;
import static busy.util.SQLUtil.DESCRIPTION;
import static busy.util.SQLUtil.DURATION;
import static busy.util.SQLUtil.ROLE_ID;
import static busy.util.SQLUtil.SERVICE_QUERY;
import static busy.util.SQLUtil.SERVICE_TYPE_ID;
import static busy.util.SQLUtil.START_DATETIME;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import busy.role.Role;
import busy.util.SecureSetter;

/**
 * Service persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ServiceDaoImpl implements ServiceDao {

    private static final String SQL_SELECT_BETWEEN_DAYS =
            SERVICE_QUERY + " WHERE ? <= " + START_DATETIME + " AND ? >= " + START_DATETIME + " AND " + ROLE_ID + "=?";

    private static final String SQL_SERVICE_TYPE_FILTER = " AND " + SERVICE_TYPE_ID + "=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ServiceDao#findBetweenDays(org.joda.time.DateTime, org.joda.time.DateTime,
     * busy.role.Role, busy.schedule.ServiceType)
     */
    @Override
    public List<Service> findBetweenDays(DateTime initDay, DateTime endDay, Role role, ServiceType serviceType) {

        if (initDay == null || endDay == null) {
            throw new IllegalArgumentException("The days between which find the services cannot be null");
        }
        
        if(role == null) {
            throw new IllegalArgumentException("The role cannot be null");
        }

        ServiceRowMapper rowMapper = new ServiceRowMapper();
        rowMapper.setRole(role);
        rowMapper.setServiceType(serviceType);

        String query = SQL_SELECT_BETWEEN_DAYS;
        List<Object> params = new ArrayList<>();
        params.add(new Timestamp(initDay.getMillis()));
        params.add(new Timestamp(endDay.getMillis()));
        params.add(role.getId());

        if (serviceType != null) {
            query += SQL_SERVICE_TYPE_FILTER;
            params.add(serviceType.getId());
        }

        return jdbcTemplate.query(query, rowMapper, params.toArray());
    }

    private class ServiceRowMapper implements RowMapper<Service> {

        private Role role;
        private ServiceType serviceType;

        public void setRole(Role role) {
            this.role = role;
        }

        public void setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
        }

        @Override
        public Service mapRow(ResultSet rs, int rowNum) throws SQLException {

            Service service = new Service();

            service.setRole(role);

            if (serviceType == null) {
                serviceType = new ServiceType();
                SecureSetter.setId(serviceType, rs.getInt(ALIAS_SERVICE_TYPE_ID));
                serviceType.setName(rs.getString(ALIAS_SERVICE_TYPE_NAME));
                serviceType.setDescription(rs.getString(DESCRIPTION));
                serviceType.setMaxBookingsPerRole(rs.getInt(BOOKINGS_PER_ROLE));
                serviceType.setDuration(rs.getInt(DURATION));
                serviceType.setCompany(role.getCompany());
            }
            service.setServiceType(serviceType);

            service.setStartTime(new DateTime(rs.getTimestamp(START_DATETIME)));

            return service;
        }

    }

}
