package job_tracker.data;

import job_tracker.data.mappers.ApplicationMapper;
import job_tracker.models.Application;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        jdbcTemplate.update("INSERT INTO Application (user_id, job_id, application_date, applied_on, status) VALUES (?, ?, ?, ?, ?)",
                application.getUserId(), application.getJobId(), application.getApplicationDate(), application.getApplicationDate(), application.getStatus().toString());
        return application;
    }

    @Override
    public boolean update(Application application) {
        return jdbcTemplate.update("UPDATE Application SET user_id = ?, job_id = ?, application_date = ?, applied_on = ?, status = ? WHERE id = ?",
                application.getUserId(), application.getJobId(), application.getApplicationDate(), application.getApplicationDate(), application.getStatus().toString(), application.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Application WHERE id = ?", id) > 0;
    }
}
