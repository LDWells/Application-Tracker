package job_tracker.domain;

import job_tracker.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.PostRepository;
import job_tracker.models.Post;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final Validator validator;

    @Autowired
    public PostService(PostRepository postRepository, Validator validator) {
        this.postRepository = postRepository;
        this.validator = validator;
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(int id) {
        return postRepository.findById(id);
    }

    public Result<Post> addPost(Post post) {
        Result<Post> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Post> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }

        result.setPayload(postRepository.add(post));
        return result;
    }

    public Result<Post> updatePost(Post post) {
        Result<Post> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Post> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }
        postRepository.update(post);
        result.setPayload(post);
        return result;
    }

    public boolean deletePost(int id) {
        return postRepository.deleteById(id);
    }
}
