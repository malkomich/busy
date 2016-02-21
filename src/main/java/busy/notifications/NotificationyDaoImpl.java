package busy.notifications;

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
public class NotificationyDaoImpl implements NotificationDao {

	private static final String SQL_UPDATE = "UPDATE " + TABLE_NOTIFICATION + " SET " + USERID + "= ?,"
			+ NOTIFICATION_TYPE + "=?," + MESSAGE + "=?," + IS_READ + "=?," + CREATE_DATE + "=? " + " WHERE " + ID
			+ "= ?";

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName(TABLE_NOTIFICATION);
		jdbcInsert.setGeneratedKeyName(ID);
		jdbcInsert.setColumnNames(Arrays.asList(USERID, NOTIFICATION_TYPE, MESSAGE, IS_READ));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * busy.notifications.NotificationDao#save(busy.notifications.Notification)
	 */
	@Override
	public void save(Notification notification) {

		if (notification.getId() > 0) {

			jdbcTemplate.update(SQL_UPDATE, notification.getUserId(), notification.getType(), notification.getMessage(),
					notification.isRead(), notification.getId());

		} else {

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(USERID, notification.getUserId());
			parameters.put(NOTIFICATION_TYPE, notification.getType());
			parameters.put(MESSAGE, notification.getMessage());
			parameters.put(IS_READ, DEFAULT);

			Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
			if (key != null) {
				SecureSetter.setId(notification, key.intValue());
			}
		}
	}

}
