package job_tracker.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.CommentRepository;
import job_tracker.models.Comment;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final Validator validator;

    @Autowired
    public CommentService(CommentRepository commentRepository, Validator validator) {
        this.commentRepository = commentRepository;
        this.validator = validator;
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment findCommentById(int id) {
        return commentRepository.findById(id);
    }

    public Comment addComment(@Valid Comment comment) {
        return commentRepository.add(comment);
    }

    public boolean updateComment(@Valid Comment comment) {
        return commentRepository.update(comment);
    }

    public boolean deleteComment(int id) {
        return commentRepository.delete(id);
    }
}
