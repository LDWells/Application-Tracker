package job_tracker.data;

import job_tracker.data.mappers.CommentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import job_tracker.models.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCommentRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Comment WHERE id = ?", new CommentMapper(), id);
    }

    @Override
    public List<Comment> findAll() {
        return jdbcTemplate.query("SELECT * FROM Comment", new CommentMapper());
    }

    @Override
    public Comment add(Comment comment) {
        jdbcTemplate.update("INSERT INTO Comment (post_id, user_id, content, comment_date) VALUES (?, ?, ?, ?)",
                comment.getPostId(), comment.getUserId(), comment.getContent(), comment.getCommentDate());
        return comment;
    }

    @Override
    public boolean update(Comment comment) {
        return jdbcTemplate.update("UPDATE Comment SET post_id = ?, user_id = ?, content = ?, comment_date = ? WHERE id = ?",
                comment.getPostId(), comment.getUserId(), comment.getContent(), comment.getCommentDate(), comment.getId()) > 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM Comment WHERE id = ?", id) > 0;
    }
}
