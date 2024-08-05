package learn.job_tracker.data.mappers;

import learn.job_tracker.models.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setTaskId(resultSet.getInt("id"));
        task.setApplicationId(resultSet.getInt("application_id"));
        task.setDescription(resultSet.getString("description"));
        if (resultSet.getDate("due_date") != null) {
            task.setDueDate(resultSet.getDate("due_date").toLocalDate());
        }
        if (resultSet.getDate("reminder_date") != null) {
            task.setReminderDate(resultSet.getDate("reminder_date").toLocalDate());
        }
        task.setStatus(TaskStatus.valueOf(resultSet.getString("status").toUpperCase()));
        return task;
    }
}
