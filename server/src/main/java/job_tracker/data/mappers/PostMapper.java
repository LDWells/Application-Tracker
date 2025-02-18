package job_tracker.data.mappers;

import job_tracker.models.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        Post post  = new Post();
        post.setId(resultSet.getInt("id"));
        post.setUserId(resultSet.getInt("user_id"));
        post.setTitle(resultSet.getString("title")); //change to company_name if this cause problems
        post.setContent(resultSet.getString("content"));
        post.setSummary(resultSet.getString(("summary")));
        if (resultSet.getDate("post_date") != null) {
            post.setPostDate(resultSet.getDate("post_date").toLocalDate());
        }
        return post;
    }



}
