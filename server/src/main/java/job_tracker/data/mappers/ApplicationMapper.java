/************************************************************************
 * Author: LaDarion Wells
 * Date Created: 8/5/2024
 ************************************************************************/

package job_tracker.data.mappers;


import job_tracker.models.Application;
import job_tracker.models.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper implements RowMapper<Application> {

    @Override
    public Application mapRow(ResultSet resultSet, int i) throws SQLException {
        Application application = new Application();
        application.setId(resultSet.getInt("id"));
        application.setUserId(resultSet.getInt("user_id"));
        application.setJobId(resultSet.getInt("job_id"));
        if (resultSet.getDate("date") != null) {
            application.setApplicationDate(resultSet.getDate("date").toLocalDate());
        }
        application.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
        return application;
    }
}
