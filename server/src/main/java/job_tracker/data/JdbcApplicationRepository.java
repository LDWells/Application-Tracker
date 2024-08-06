package job_tracker.data;

import job_tracker.data.mappers.ApplicationMapper;
import job_tracker.models.Application;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcApplicationRepository implements ApplicationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Application findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Application WHERE id = ?", new ApplicationMapper(), id);
    }

    @Override
    public List<Application> findAll() {
        return jdbcTemplate.query("SELECT * FROM Application", new ApplicationMapper());
    }

    @Override
    public Application add(Application application) {
        final String sql ="INSERT INTO Application (user_id, job_id, application_date, applied_on, status) "
                + " VALUES (?, ?, ?, ?, ?);";

        //Needed to auto-generate primary id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, application.getUserId());
            ps.setInt(2, application.getJobId());
            ps.setDate(3, application.getApplicationDate() == null ? null : Date.valueOf(application.getApplicationDate()));
            ps.setString(4, application.getAppliedOn());
            ps.setString(5, application.getStatus().toString());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        application.setId(keyHolder.getKey().intValue());

        return application;
    }

    @Override
    public boolean update(Application application) {
        return jdbcTemplate.update("UPDATE Application SET user_id = ?, job_id = ?, application_date = ?, applied_on = ?, status = ? WHERE id = ?",
                application.getUserId(), application.getJobId(), application.getApplicationDate(), application.getAppliedOn(), application.getStatus().toString(), application.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        jdbcTemplate.update("DELETE FROM Task WHERE application_id = ? ", id); //need to first remove from referenced table
        return jdbcTemplate.update("DELETE FROM Application WHERE id = ?", id) > 0;
    }
}
