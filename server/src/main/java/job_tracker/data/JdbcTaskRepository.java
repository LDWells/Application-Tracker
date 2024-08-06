package job_tracker.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import job_tracker.data.TaskRepository;
import job_tracker.data.mappers.TaskMapper;
import job_tracker.models.Task;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
        final String sql = "INSERT INTO Task (application_id, description, due_date, reminder_date, status) "
                + " VALUES (?, ?, ?, ?, ?);";

        //Needed to auto-generate primary id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, task.getApplicationId());
            ps.setString(2, task.getDescription());
            ps.setDate(3, task.getDueDate() == null ? null : Date.valueOf(task.getDueDate()));
            ps.setDate(4, task.getReminderDate() == null ? null : Date.valueOf(task.getReminderDate()));
            ps.setString(5, task.getStatus().toString());


            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        task.setId(keyHolder.getKey().intValue());

        return task;
    }

    @Override
    public boolean update(Task task) {
        return jdbcTemplate.update("UPDATE Task SET application_id = ?, description = ?, due_date = ?, reminder_date = ?, status = ? WHERE id = ?",
                task.getApplicationId(), task.getDescription(), task.getDueDate(), task.getReminderDate(), task.getStatus().toString(), task.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Task WHERE id = ?", id) > 0;
    }
}
