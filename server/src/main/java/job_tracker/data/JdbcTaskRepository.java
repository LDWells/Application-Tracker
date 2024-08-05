package job_tracker.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import job_tracker.data.TaskRepository;
import job_tracker.data.mappers.TaskMapper;
import job_tracker.models.Task;

import java.util.List;

@Repository
public class JdbcTaskRepository implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Task findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Task WHERE id = ?", new TaskMapper(), id);
    }

    @Override
    public List<Task> findAll() {
        return jdbcTemplate.query("SELECT * FROM Task", new TaskMapper());
    }

    @Override
    public Task add(Task task) {
        jdbcTemplate.update("INSERT INTO Task (application_id, description, due_date, reminder_date, status) VALUES (?, ?, ?, ?, ?)",
                task.getApplicationId(), task.getDescription(), task.getDueDate(), task.getReminderDate(), task.getStatus().toString());
        return task;
    }

    @Override
    public boolean update(Task task) {
        return jdbcTemplate.update("UPDATE Task SET application_id = ?, description = ?, due_date = ?, reminder_date = ?, status = ? WHERE id = ?",
                task.getApplicationId(), task.getDescription(), task.getDueDate(), task.getReminderDate(), task.getStatus().toString(), task.getId()) > 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM Task WHERE id = ?", id) > 0;
    }
}
