package job_tracker.data;

import job_tracker.data.mappers.JobMapper;
import job_tracker.models.Job;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcJobRepository implements JobRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcJobRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Job findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Job WHERE id = ?", new JobMapper(), id);
    }

    @Override
    public List<Job> findAll() {
        return jdbcTemplate.query("SELECT * FROM Job", new JobMapper());
    }

    @Override
    public Job add(Job job) {
        final String sql = "INSERT INTO Job (title, description, company_id) "
                + " VALUES (?, ?, ?);";

        //Needed to auto-generate primary id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, job.getTitle());
            ps.setString(2, job.getDescription());
            ps.setInt(3, job.getCompanyId());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        job.setId(keyHolder.getKey().intValue());

        return job;
    }

    @Override
    public boolean update(Job job) {
        return jdbcTemplate.update("UPDATE Job SET title = ?, description = ?, company_id = ? WHERE id = ?",
                job.getTitle(), job.getDescription(), job.getCompanyId(), job.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        // First delete tasks related to the applications of the job
        jdbcTemplate.update("DELETE FROM Task WHERE application_id IN (SELECT id FROM Application WHERE job_id = ?)", id);

        // Then delete applications related to the job
        jdbcTemplate.update("DELETE FROM Application WHERE job_id = ?", id);

        // Finally delete the job
        return jdbcTemplate.update("DELETE FROM Job WHERE id = ?", id) > 0;
    }
}
