package job_tracker.data;

import job_tracker.data.mappers.UserMapper;
import job_tracker.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM `User` WHERE id = ?", new UserMapper(), id);
    }

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM `User` WHERE email = ?", new UserMapper(), email);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM `User`", new UserMapper());
    }

    @Override
    public User add(User user) {
        final String sql = "INSERT INTO `User` (google_id, username, email, password, role) "
                + " VALUES (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getGoogleId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole().toString());


            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setId(keyHolder.getKey().intValue());

        return user;
    }

    @Override
    public boolean update(User user) {
        return jdbcTemplate.update("UPDATE `User` SET google_id = ?, username = ?, email = ?, password = ?, role = ? WHERE id = ?",
                user.getGoogleId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole().toString(), user.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        // Delete related tasks first
        jdbcTemplate.update("DELETE FROM Task WHERE application_id IN (SELECT id FROM Application WHERE user_id = ?)", id);

        // Delete related applications
        jdbcTemplate.update("DELETE FROM Application WHERE user_id = ?", id);

        // Delete related comments
        jdbcTemplate.update("DELETE FROM Comment WHERE user_id = ?", id);

        // Delete related posts
        jdbcTemplate.update("DELETE FROM Post WHERE user_id = ?", id);

        // Finally, delete the user
        return jdbcTemplate.update("DELETE FROM `User` WHERE id = ?", id) > 0;
    }
}
