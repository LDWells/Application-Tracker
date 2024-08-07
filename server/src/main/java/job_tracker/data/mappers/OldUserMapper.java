/************************************************************************
 * Author: LaDarion Wells
 * Date Created: 8/5/2024
 ************************************************************************/
package job_tracker.data.mappers;

import job_tracker.models.OldUser;
import org.springframework.jdbc.core.RowMapper;
import job_tracker.models.Role;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OldUserMapper implements RowMapper<OldUser> {

    @Override
    public OldUser mapRow(ResultSet resultSet, int i) throws SQLException {
        OldUser oldUser = new OldUser();
        oldUser.setId(resultSet.getInt("id"));
        oldUser.setGoogleId(resultSet.getString("google_id"));
        oldUser.setUsername(resultSet.getString("username"));
        oldUser.setEmail(resultSet.getString("email"));
        oldUser.setPassword(resultSet.getString("password"));
        oldUser.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
        return oldUser;
    }
}
