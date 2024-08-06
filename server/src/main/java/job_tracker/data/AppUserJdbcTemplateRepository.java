//package job_tracker.data;
//
//import job_tracker.data.mappers.AppUserMapper;
//import job_tracker.models.AppUser;
//import job_tracker.models.Role;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.PreparedStatement;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@Repository
//public class AppUserJdbcTemplateRepository implements AppUserRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    @Transactional
//    public AppUser findByUsername(String username) {
//        List<String> roles = new ArrayList<>();
//        roles.add(Role.USER.getName());
//        roles.add(Role.ADMIN.getName());
//
//        final String sql = "select app_user_id, username, password_hash, disabled "
//                + "from app_user "
//                + "where username = ?;";
//
//        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
//                .stream()
//                .findFirst().orElse(null);
//    }
//
//    @Override
//    @Transactional
//    public AppUser findByEmail(String email) {
//        List<String> roles = List.of(Role.USER.getName(), Role.ADMIN.getName());
//        final String sql = "SELECT app_user_id, username, password_hash, disabled FROM app_user WHERE email = ?;";
//        return jdbcTemplate.query(sql, new AppUserMapper(roles), email)
//                .stream()
//                .findFirst().orElse(null);
//    }
//
//    @Override
//    @Transactional
//    public AppUser findByGoogleId(String googleId) {
//        List<String> roles = List.of(Role.USER.getName(), Role.ADMIN.getName());
//        final String sql = "SELECT app_user_id, username, password_hash, disabled FROM app_user WHERE google_id = ?;";
//        return jdbcTemplate.query(sql, new AppUserMapper(roles), googleId)
//                .stream()
//                .findFirst().orElse(null);
//    }
//
//    @Override
//    @Transactional
//    public List<AppUser> findAll() {
//        List<String> roles = new ArrayList<>();
//        roles.add(Role.USER.getName());
//        roles.add(Role.ADMIN.getName());
//
//        final String sql = "select app_user_id, username, password_hash, disabled "
//                + "from app_user;";
//
//        return jdbcTemplate.query(sql, new AppUserMapper(roles));
//    }
//
//    @Override
//    @Transactional
//    public AppUser create(AppUser user) {
//
//        final String sql = "insert into app_user (username, password_hash) values (?, ?);";
//
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        int rowsAffected = jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, user.getUsername());
//            ps.setString(2, user.getPassword());
//            return ps;
//        }, keyHolder);
//
//        if (rowsAffected <= 0) {
//            return null;
//        }
//
//        user.setAppUserId(keyHolder.getKey().intValue());
//
//        if (user.getGoogleId() != null) {
//            user.setGoogleId(keyHolder.getKey().toString());
//        }
//
//        updateRoles(user);
//
//        return user;
//    }
//
//    @Override
//    @Transactional
//    public void update(AppUser user) {
//
//        final String sql = "update app_user set "
//                + "username = ?, "
//                + "disabled = ? "
//                + "where app_user_id = ?";
//
//        jdbcTemplate.update(sql,
//                user.getUsername(), !user.isEnabled(), user.getAppUserId());
//
//        updateRoles(user);
//    }
//
//    @Override
//    public boolean deleteById(AppUser user) {
//        jdbcTemplate.update("delete from post where user_id = ?", user.getAppUserId());
//        jdbcTemplate.update("delete from comment where user_id = ?", user.getAppUserId());
//        jdbcTemplate.update("delete from application where user_id = ?", user.getAppUserId());
//        return jdbcTemplate.update("delete from user where id = ?;", user.getAppUserId()) > 0;
//    }
//
//    //    private void updateRoles(AppUser user) {
////        // delete all roles, then re-add
////        jdbcTemplate.update("update app_user where app_user_id = ?;", user.getAppUserId());
////
////        Collection<GrantedAuthority> authorities = user.getAuthorities();
////
////        if (authorities == null) {
////            return;
////        }
////
////        for (String role : AppUser.convertAuthoritiesToRoles(authorities)) {
////            String sql = "insert into app_user_role (app_user_id, app_role_id) "
////                    + "select ?, app_role_id from app_role where `name` = ?;";
////            jdbcTemplate.update(sql, user.getAppUserId(), role);
////        }
////    }
//    private void updateRoles(AppUser user) {
//        // We nee to delete all roles, then re-add
//        jdbcTemplate.update("DELETE FROM app_user_role WHERE app_user_id = ?", user.getAppUserId());
//
//        Collection<GrantedAuthority> authorities = user.getAuthorities();
//
//        if (authorities != null) {
//            for (String role : AppUser.convertAuthoritiesToRoles(authorities)) {
//                String sql = "INSERT INTO app_user_role (app_user_id, app_role_id) "
//                        + "SELECT ?, app_role_id FROM app_role WHERE `name` = ?;";
//                jdbcTemplate.update(sql, user.getAppUserId(), role);
//            }
//        }
//    }
//}

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
        List<String> roles = List.of(Role.USER.getName(), Role.ADMIN.getName());
        final String sql = "SELECT id, google_id, username, email, password, role FROM `User` WHERE username = ?;";
        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public AppUser findByEmail(String email) {
        List<String> roles = List.of(Role.USER.getName(), Role.ADMIN.getName());
        final String sql = "SELECT id, google_id, username, email, password, role FROM `User` WHERE email = ?;";
        return jdbcTemplate.query(sql, new AppUserMapper(roles), email)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public AppUser findByGoogleId(String googleId) {
        List<String> roles = List.of(Role.USER.getName(), Role.ADMIN.getName());
        final String sql = "SELECT id, google_id, username, email, password, role FROM `User` WHERE google_id = ?;";
        return jdbcTemplate.query(sql, new AppUserMapper(roles), googleId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public List<AppUser> findAll() {
        List<String> roles = List.of(Role.USER.getName(), Role.ADMIN.getName());
        final String sql = "SELECT id, google_id, username, email, password, role FROM `User`;";
        return jdbcTemplate.query(sql, new AppUserMapper(roles));
    }

    @Override
    @Transactional
    public AppUser create(AppUser user) {
        final String sql = "INSERT INTO `User` (google_id, username, email, password, role) VALUES (?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getGoogleId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAuthorities().stream()
                    .map(auth -> auth.getAuthority().substring(5)) // Remove 'ROLE_' prefix
                    .findFirst().orElse("USER")); // Default role
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());
        updateRoles(user);

        return user;
    }

    @Override
    @Transactional
    public void update(AppUser user) {
        final String sql = "UPDATE `User` SET google_id = ?, username = ?, email = ?, password = ?, role = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getGoogleId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(auth -> auth.getAuthority().substring(5))
                        .findFirst().orElse("USER"),
                user.getAppUserId());

        updateRoles(user);
    }

    @Override
    @Transactional
    public boolean deleteById(AppUser user) {
        jdbcTemplate.update("DELETE FROM post WHERE user_id = ?", user.getAppUserId());
        jdbcTemplate.update("DELETE FROM comment WHERE user_id = ?", user.getAppUserId());
        jdbcTemplate.update("DELETE FROM application WHERE user_id = ?", user.getAppUserId());
        return jdbcTemplate.update("DELETE FROM `User` WHERE id = ?", user.getAppUserId()) > 0;
    }

    private void updateRoles(AppUser user) {
        jdbcTemplate.update("DELETE FROM app_user_role WHERE app_user_id = ?", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities != null) {
            for (String role : AppUser.convertAuthoritiesToRoles(authorities)) {
                String sql = "INSERT INTO app_user_role (app_user_id, app_role_id) "
                        + "SELECT ?, app_role_id FROM app_role WHERE `name` = ?;";
                jdbcTemplate.update(sql, user.getAppUserId(), role);
            }
        }
    }
}
