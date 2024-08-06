package job_tracker.data;

import job_tracker.models.Comment;

import java.util.List;

public interface CommentRepository {
    Comment findById(int id);
    List<Comment> findAll();
    Comment add(Comment comment);
    boolean update(Comment comment);
    boolean deleteById(int id);
}
