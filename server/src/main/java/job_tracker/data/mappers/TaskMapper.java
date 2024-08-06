/************************************************************************
 * Author: LaDarion Wells
 * Date Created: 8/5/2024
 ************************************************************************/
package job_tracker.data.mappers;

import job_tracker.models.Task;
import job_tracker.models.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        task.setApplicationId(resultSet.getInt("application_id"));
        task.setDescription(resultSet.getString("description"));
        if (resultSet.getDate("due_date") != null) {
            task.setDueDate(resultSet.getDate("due_date").toLocalDate());
        }
        if (resultSet.getDate("reminder_date") != null) {
            task.setReminderDate(resultSet.getDate("reminder_date").toLocalDate());
        }
        task.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
        return task;
    }
}
