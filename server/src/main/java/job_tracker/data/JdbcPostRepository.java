package job_tracker.data;

import job_tracker.data.mappers.PostMapper;
import job_tracker.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPostRepository implements PostRepository{

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
        jdbcTemplate.update("INSERT INTO Post (user_id, title, content, post_date) VALUES (?, ?, ?, ?)",
                post.getUserId(), post.getTitle(), post.getContent(), post.getPostDate());
        return post;
    }

    @Override
    public boolean update(Post post) {
        return jdbcTemplate.update("UPDATE Post SET user_id = ?, title = ?, content = ?, post_date = ? WHERE id = ?",
                post.getUserId(), post.getTitle(), post.getContent(), post.getPostDate(), post.getId()) > 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM Post WHERE id = ?", id) > 0;
    }
}
