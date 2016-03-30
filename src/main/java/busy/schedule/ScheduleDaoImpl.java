package busy.schedule;

import static busy.util.SQLUtil.ALIAS_DAY_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_HOUR_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_WEEK_SCHEDULE_ID;
import static busy.util.SQLUtil.ALIAS_YEAR_SCHEDULE_ID;
import static busy.util.SQLUtil.BRANCHID;
import static busy.util.SQLUtil.DAY_OF_WEEK;
import static busy.util.SQLUtil.END_TIME;
import static busy.util.SQLUtil.IS_DEFAULT;
import static busy.util.SQLUtil.START_TIME;
import static busy.util.SQLUtil.WEEK_OF_YEAR;
import static busy.util.SQLUtil.YEAR;
import static busy.util.SQLUtil.YEAR_SCHEDULE_QUERY;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import busy.company.Branch;
import busy.util.SecureSetter;

/**
 * Schedule persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    private static final String SQL_SELECT_YEAR_FROM_BRANCH =
            YEAR_SCHEDULE_QUERY + " WHERE " + BRANCHID + "=? AND " + YEAR + "=?";

    private static final String SQL_SELECT_WEEKS_OF_YEAR =
            YEAR_SCHEDULE_QUERY + " WHERE " + BRANCHID + "=? AND " + YEAR + "=?";

    private static final String SQL_SELECT_DEFAULT_WEEK =
            YEAR_SCHEDULE_QUERY + " WHERE " + IS_DEFAULT + "= 't' AND " + BRANCHID + "=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findByBranchAndYear(busy.company.Branch, int)
     */
    @Override
    public YearSchedule findYearFromBranch(Branch branch, int year) {

        YearScheduleRowMapper rowMapper = new YearScheduleRowMapper();
        rowMapper.setBranch(branch);

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_YEAR_FROM_BRANCH, rowMapper, branch.getId(), year);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findByYearAndWeeks(int, int[])
     */
    @Override
    public List<WeekSchedule> findWeeksFromBranch(Branch branch, int year, int... weeks) {

        int numOfWeeks = weeks.length;

        String query = (numOfWeeks > 0) ? SQL_SELECT_WEEKS_OF_YEAR + " AND (" : SQL_SELECT_WEEKS_OF_YEAR;
        for (int i = 0; i < numOfWeeks; i++) {
            query += WEEK_OF_YEAR + "=?" + ((i < weeks.length - 1) ? " OR " : ")");
        }

        Object[] params = new Object[numOfWeeks + 2];
        params[0] = branch.getId();
        params[1] = year;
        for (int i = 0; i < numOfWeeks; i++) {
            params[i + 2] = weeks[i];
        }

        WeekScheduleRowMapper rowMapper = new WeekScheduleRowMapper();
        rowMapper.setBranch(branch);

        try {

            return jdbcTemplate.queryForObject(query, rowMapper, params);

        } catch (EmptyResultDataAccessException e) {

            return new ArrayList<WeekSchedule>();
        }
    }

    /*
     * (non-Javadoc)
     * @see busy.schedule.ScheduleDao#findDefaultWeek()
     */
    @Override
    public WeekSchedule findDefaultWeek(Branch branch) {

        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_DEFAULT_WEEK, new WeekScheduleRowMapper(), branch.getId())
                    .get(0);

        } catch (EmptyResultDataAccessException | IndexOutOfBoundsException e) {

            return null;
        }
    }

    private class YearScheduleRowMapper implements RowMapper<YearSchedule> {

        private Branch branch;

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        @Override
        public YearSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {

            // Parse year schedule
            YearSchedule yearSchedule = null;

            do {
                if (yearSchedule == null) {
                    yearSchedule = new YearSchedule();
                    SecureSetter.setId(yearSchedule, rs.getInt(ALIAS_YEAR_SCHEDULE_ID));
                    yearSchedule.setBranch(branch);
                    yearSchedule.setYear(rs.getInt(YEAR));
                }

                // Parse week schedules
                WeekSchedule weekSchedule = null;

                do {
                    if (weekSchedule == null) {
                        weekSchedule = new WeekSchedule();
                        SecureSetter.setId(weekSchedule, rs.getInt(ALIAS_WEEK_SCHEDULE_ID));
                        weekSchedule.setWeekOfYear(rs.getInt(WEEK_OF_YEAR));
                        weekSchedule.setDefault(rs.getBoolean(IS_DEFAULT));
                        weekSchedule.setYearSchedule(yearSchedule);
                    }

                    // Parse day schedules
                    DaySchedule daySchedule = null;

                    do {
                        if (daySchedule == null) {
                            daySchedule = new DaySchedule();
                            SecureSetter.setId(daySchedule, rs.getInt(ALIAS_DAY_SCHEDULE_ID));
                            daySchedule.setDayOfWeek(rs.getInt(DAY_OF_WEEK));
                            daySchedule.setWeekSchedule(weekSchedule);
                        }

                        // Parse hour schedules
                        HourSchedule hourSchedule = new HourSchedule();
                        SecureSetter.setId(hourSchedule, rs.getInt(ALIAS_HOUR_SCHEDULE_ID));
                        hourSchedule.setStartTime(new LocalTime(rs.getTime(START_TIME)));
                        hourSchedule.setEndTime(new LocalTime(rs.getTime(END_TIME)));
                        hourSchedule.setDaySchedule(daySchedule);

                        daySchedule.addHourSchedule(hourSchedule);

                    } while (rs.next() && rs.getInt(ALIAS_DAY_SCHEDULE_ID) == daySchedule.getId());

                    weekSchedule.addDaySchedule(daySchedule);

                } while (rs.next() && rs.getInt(ALIAS_WEEK_SCHEDULE_ID) == weekSchedule.getId());

                yearSchedule.addWeekSchedule(weekSchedule);

            } while (rs.next());

            return yearSchedule;
        }

    }

    private class WeekScheduleRowMapper implements RowMapper<List<WeekSchedule>> {

        private Branch branch;

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        @Override
        public List<WeekSchedule> mapRow(ResultSet rs, int rowNum) throws SQLException {

            List<WeekSchedule> weekScheduleList = new ArrayList<WeekSchedule>();

            YearSchedule yearSchedule = new YearSchedule();
            SecureSetter.setId(yearSchedule, rs.getInt(ALIAS_YEAR_SCHEDULE_ID));
            yearSchedule.setBranch(branch);
            yearSchedule.setYear(rs.getInt(YEAR));

            do {
                // Parse week schedules
                WeekSchedule weekSchedule = null;

                do {
                    if (weekSchedule == null) {
                        weekSchedule = new WeekSchedule();
                        SecureSetter.setId(weekSchedule, rs.getInt(ALIAS_WEEK_SCHEDULE_ID));
                        weekSchedule.setWeekOfYear(rs.getInt(WEEK_OF_YEAR));
                        weekSchedule.setDefault(rs.getBoolean(IS_DEFAULT));
                        weekSchedule.setYearSchedule(yearSchedule);
                    }

                    // Parse day schedules
                    DaySchedule daySchedule = null;

                    do {
                        if (daySchedule == null) {
                            daySchedule = new DaySchedule();
                            SecureSetter.setId(daySchedule, rs.getInt(ALIAS_DAY_SCHEDULE_ID));
                            daySchedule.setDayOfWeek(rs.getInt(DAY_OF_WEEK));
                            daySchedule.setWeekSchedule(weekSchedule);
                        }

                        // Parse hour schedules
                        HourSchedule hourSchedule = new HourSchedule();
                        SecureSetter.setId(hourSchedule, rs.getInt(ALIAS_HOUR_SCHEDULE_ID));
                        hourSchedule.setStartTime(new LocalTime(rs.getTime(START_TIME)));
                        hourSchedule.setEndTime(new LocalTime(rs.getTime(END_TIME)));
                        hourSchedule.setDaySchedule(daySchedule);

                        daySchedule.addHourSchedule(hourSchedule);

                    } while (rs.next() && rs.getInt(ALIAS_DAY_SCHEDULE_ID) == daySchedule.getId());

                    weekSchedule.addDaySchedule(daySchedule);

                } while (rs.next() && rs.getInt(ALIAS_WEEK_SCHEDULE_ID) == weekSchedule.getId());

                weekScheduleList.add(weekSchedule);

            } while (rs.next());

            return weekScheduleList;
        }
    }

}
