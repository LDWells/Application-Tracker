package job_tracker.data;

import job_tracker.data.mappers.UserMapper;
import job_tracker.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        jdbcTemplate.update("INSERT INTO `User` (google_id, username, email, password, role) VALUES (?, ?, ?, ?, ?)",
                user.getGoogleId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
        return user;
    }

    @Override
    public boolean update(User user) {
        return jdbcTemplate.update("UPDATE `User` SET google_id = ?, username = ?, email = ?, password = ?, role = ? WHERE id = ?",
                user.getGoogleId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM `User` WHERE id = ?", id) > 0;
    }
}
