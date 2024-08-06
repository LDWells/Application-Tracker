package job_tracker.data;

import job_tracker.data.mappers.CommentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import job_tracker.models.Comment;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
        final String sql = "INSERT INTO Comment (post_id, user_id, content, comment_date) "
                + " VALUES (?, ?, ?, ?);";

        //Needed to auto-generate primary id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comment.getPostId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());
            ps.setDate(4, comment.getCommentDate() == null ? null : Date.valueOf(comment.getCommentDate()));
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        comment.setId(keyHolder.getKey().intValue());

        return comment;
    }

    @Override
    public boolean update(Comment comment) {
        return jdbcTemplate.update("UPDATE Comment SET post_id = ?, user_id = ?, content = ?, comment_date = ? WHERE id = ?",
                comment.getPostId(), comment.getUserId(), comment.getContent(), comment.getCommentDate(), comment.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Comment WHERE id = ?", id) > 0;
    }
}
