package job_tracker.data;

import job_tracker.data.mappers.AppUserMapper;
import job_tracker.models.AppUser;
import job_tracker.models.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AppUser findByUsername(String username) {
        List<String> roles = new ArrayList<>();
        roles.add(Role.USER.getName());
        roles.add(Role.ADMIN.getName());

        final String sql = "select app_user_id, username, password_hash, disabled "
                + "from app_user "
                + "where username = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public List<AppUser> findAll() {
        List<String> roles = new ArrayList<>();
        roles.add(Role.USER.getName());
        roles.add(Role.ADMIN.getName());

        final String sql = "select app_user_id, username, password_hash, disabled "
                + "from app_user;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles));
    }

    @Override
    @Transactional
    public AppUser create(AppUser user) {

        final String sql = "insert into app_user (username, password_hash) values (?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());

        if (user.getGoogleId() != null)
        {
            user.setGoogleId(keyHolder.getKey().toString());
        }

        updateRoles(user);

        return user;
    }

    @Override
    @Transactional
    public void update(AppUser user) {

        final String sql = "update app_user set "
                + "username = ?, "
                + "disabled = ? "
                + "where app_user_id = ?";

        jdbcTemplate.update(sql,
                user.getUsername(), !user.isEnabled(), user.getAppUserId());

        updateRoles(user);
    }

    @Override
    public boolean deleteById(AppUser user) {
        jdbcTemplate.update("delete from post where user_id = ?", user.getAppUserId());
        jdbcTemplate.update("delete from comment where user_id = ?", user.getAppUserId());
        jdbcTemplate.update("delete from application where user_id = ?", user.getAppUserId());
        return jdbcTemplate.update("delete from user where id = ?;", user.getAppUserId()) > 0;
    }

    private void updateRoles(AppUser user) {
        // delete all roles, then re-add
        jdbcTemplate.update("update app_user where app_user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (String role : AppUser.convertAuthoritiesToRoles(authorities)) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) "
                    + "select ?, app_role_id from app_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role);
        }
    }
}
