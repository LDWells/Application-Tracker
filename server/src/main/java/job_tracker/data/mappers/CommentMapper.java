/************************************************************************
 * Author: LaDarion Wells
 * Date Created: 8/5/2024
 ************************************************************************/
package job_tracker.data.mappers;


import job_tracker.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment  = new Comment();
        comment.setCommentId(resultSet.getInt("id"));
        comment.setPostId(resultSet.getInt("post_id"));
        comment.setJobId(resultSet.getInt("job_id"));
        comment.setContent(resultSet.getString("content"));
        if (resultSet.getDate("comment_date") != null) {
            comment.setCommentDate(resultSet.getDate("comment_date").toLocalDate());
        }
        return coment;
    }
}
