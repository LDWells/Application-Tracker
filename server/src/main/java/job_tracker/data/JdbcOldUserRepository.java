package job_tracker.data;

import job_tracker.data.mappers.OldUserMapper;
import job_tracker.models.OldUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcOldUserRepository implements OldUserRepository
{

    private final JdbcTemplate jdbcTemplate;

    public JdbcOldUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OldUser findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM `User` WHERE id = ?", new OldUserMapper(), id);
    }

    @Override
    public OldUser findByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM `User` WHERE email = ?", new OldUserMapper(), email);
    }

    @Override
    public List<OldUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM `User`", new OldUserMapper());
    }

    @Override
    public OldUser add(OldUser oldUser) {
        final String sql = "INSERT INTO `User` (google_id, username, email, password, role) "
                + " VALUES (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, oldUser.getGoogleId());
            ps.setString(2, oldUser.getUsername());
            ps.setString(3, oldUser.getEmail());
            ps.setString(4, oldUser.getPassword());
            ps.setString(5, oldUser.getRole().toString());


            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        oldUser.setId(keyHolder.getKey().intValue());

        return oldUser;
    }

    @Override
    public OldUser save(OldUser oldUser) {
        final String sql = "INSERT INTO `User` (google_id, username, email, password, role) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, oldUser.getGoogleId());
            ps.setString(2, oldUser.getUsername());
            ps.setString(3, oldUser.getEmail());
            ps.setString(4, oldUser.getPassword());
            ps.setString(5, oldUser.getRole().name());
            return ps;
        }, keyHolder);

        oldUser.setId(keyHolder.getKey().intValue());
        return oldUser;
    }

    @Override
    public boolean update(OldUser oldUser) {
        return jdbcTemplate.update("UPDATE `User` SET google_id = ?, username = ?, email = ?, password = ?, role = ? WHERE id = ?",
                oldUser.getGoogleId(), oldUser.getUsername(), oldUser.getEmail(), oldUser.getPassword(), oldUser.getRole().toString(), oldUser.getId()) > 0;
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
