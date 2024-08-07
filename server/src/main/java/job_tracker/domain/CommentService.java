package job_tracker.domain;

import job_tracker.models.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.CommentRepository;
import job_tracker.models.Comment;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment findCommentById(int id) {
        return commentRepository.findById(id);
    }

    public Result<Comment> addComment(Comment comment) {
        Result<Comment> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Comment> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        result.setPayload(commentRepository.add(comment));
        return result;
    }

    public Result<Comment> updateComment(Comment comment) {
        Result<Comment> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Comment> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        commentRepository.update(comment);
        result.setPayload(comment);
        return result;
    }

    public boolean deleteComment(int id) {
        return commentRepository.deleteById(id);
    }
}
