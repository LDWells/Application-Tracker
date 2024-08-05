package job_tracker.data;

import job_tracker.data.mappers.JobMapper;
import job_tracker.models.Job;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        jdbcTemplate.update("INSERT INTO Job (title, description, company_id) VALUES (?, ?, ?)",
                job.getTitle(), job.getDescription(), job.getCompanyId());
        return job;
    }

    @Override
    public boolean update(Job job) {
        return jdbcTemplate.update("UPDATE Job SET title = ?, description = ?, company_id = ? WHERE id = ?",
                job.getTitle(), job.getDescription(), job.getCompanyId(), job.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Job WHERE id = ?", id) > 0;
    }
}
