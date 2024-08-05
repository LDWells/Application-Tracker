package learn.job_tracker.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Application {
    public static class ApplicationMapper implements RowMapper<Application> {

        @Override
        public Application mapRow(ResultSet resultSet, int i) throws SQLException {
            Application application = new Application();
            application.setApplicationId(resultSet.getInt("application_id"));
            application.setApplicationId(resultSet.getInt("user_id"));
            application.setApplicationId(resultSet.getInt("job_id"));
            if (resultSet.getDate("date") != null) {
                application.setDate(resultSet.getDate("date").toLocalDate());
            }
            application.setStatus(ApplicationStatus.valueOf(resultSet.getString("status").toUpperCase()));
            return application;
        }
        }
}
