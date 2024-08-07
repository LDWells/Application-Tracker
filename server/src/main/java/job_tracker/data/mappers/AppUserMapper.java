package job_tracker.data.mappers;

import job_tracker.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserMapper implements RowMapper<AppUser>
{
    @Override
    public AppUser mapRow(ResultSet resultSet, int i) throws SQLException
    {
        AppUser user = new AppUser();
        user.setAppUserId(resultSet.getInt("app_user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password_hash"));
        user.setDisabled(resultSet.getBoolean("disabled"));
        return user;
    }
}
