package job_tracker.data;

import job_tracker.models.Post;

import java.util.List;

public interface PostRepository {
    Post findById(int id);
    List<Post> findAll();
    Post add(Post post);
    boolean update(Post post);
    boolean deleteById(int id);
}
