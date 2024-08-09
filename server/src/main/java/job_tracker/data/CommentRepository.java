package job_tracker.data;

import job_tracker.models.Comment;

import java.util.List;

public interface CommentRepository {
    Comment findByCommentId(int id);
    List<Comment> findByPostId(int id);
    List<Comment> findAll();
    Comment add(Comment comment);
    boolean update(Comment comment);
    boolean deleteById(int id);
}
