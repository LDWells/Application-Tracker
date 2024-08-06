package job_tracker.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.PostRepository;
import job_tracker.models.Post;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

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

    public Post addPost(@Valid Post post) {
        return postRepository.add(post);
    }

    public boolean updatePost(@Valid Post post) {
        return postRepository.update(post);
    }

    public boolean deletePost(int id) {
        return postRepository.deleteById(id);
    }
}
