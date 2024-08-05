package learn.job_tracker.data.mappers;


import learn.job_tracker.models.Application;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper implements RowMapper<Application> {

    @Override
    public Application mapRow(ResultSet resultSet, int i) throws SQLException {
        Application application = new Application();
        application.setApplicationId(resultSet.getInt("id"));
        application.setUserId(resultSet.getInt("user_id"));
        application.setJobId(resultSet.getInt("job_id"));
        if (resultSet.getDate("date") != null) {
            application.setDate(resultSet.getDate("date").toLocalDate());
        }
        application.setStatus(ApplicationStatus.valueOf(resultSet.getString("status").toUpperCase()));
        return application;
    }
}
