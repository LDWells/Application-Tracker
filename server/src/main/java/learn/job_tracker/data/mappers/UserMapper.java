package learn.job_tracker.data.mappers;

import learn.job_tracker.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("id"));
        user.setGoogleId(resultSet.getString("google_id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setStatus(UserStatus.valueOf(resultSet.getString("status").toUpperCase()));
        return user;
    }
}
