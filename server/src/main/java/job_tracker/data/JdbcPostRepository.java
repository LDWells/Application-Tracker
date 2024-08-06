package job_tracker.data;

import job_tracker.data.mappers.PostMapper;
import job_tracker.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Post findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Post WHERE id = ?", new PostMapper(), id);
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query("SELECT * FROM Post", new PostMapper());
    }

    @Override
    public Post add(Post post) {
        final String sql = "INSERT INTO Post (user_id, title, content, post_date) "
                + " VALUES (?, ?, ?, ?);";

        //Needed to auto-generate primary id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getContent());
            ps.setDate(4, post.getPostDate() == null ? null : Date.valueOf(post.getPostDate()));


            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        post.setId(keyHolder.getKey().intValue());
        return post;
    }

    @Override
    public boolean update(Post post) {
        return jdbcTemplate.update("UPDATE Post SET user_id = ?, title = ?, content = ?, post_date = ? WHERE id = ?",
                post.getUserId(), post.getTitle(), post.getContent(), post.getPostDate(), post.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        // First delete comments related to the post
        jdbcTemplate.update("DELETE FROM Comment WHERE post_id = ?", id);

        // Then delete the post
        return jdbcTemplate.update("DELETE FROM Post WHERE id = ?", id) > 0;
    }
}
