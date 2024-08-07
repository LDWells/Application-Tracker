package job_tracker.domain;

import job_tracker.data.PostRepository;
import job_tracker.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    PostService service;

    @MockBean
    PostRepository repository;

    @Test
    void shouldFindById(){
        Post expected = makePost();
        when(repository.findById(1)).thenReturn(expected);
        Post actual = service.findPostById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddWhenValid(){
        Post expected = makePost();
        Post arg = makePost();

        when(repository.add(arg)).thenReturn(expected);
        Result<Post> result = service.addPost(arg);
        assertNotNull(result.getPayload());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidTitle(){
        Post post = makePost();
        post.setTitle(null);
        Result<Post> result = service.addPost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidContent(){
        Post post = makePost();
        post.setContent(null);
        Result<Post> result = service.addPost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidPostDate(){
        Post post = makePost();
        post.setPostDate(null);
        Result<Post> result = service.addPost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate(){
        Post post = makePost();

        post.setContent("Testing Post");
        when(repository.update(post)).thenReturn(true);
        Result<Post> result = service.updatePost(post);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidTitle(){
        Post post = makePost();
        post.setTitle(null);
        Result<Post> result = service.updatePost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidContent(){
        Post post = makePost();
        post.setContent(null);
        Result<Post> result = service.updatePost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidPostDate(){
        Post post = makePost();
        post.setPostDate(null);
        Result<Post> result = service.updatePost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidId(){
        Post post = makePost();
        post.setId(-5);
        Result<Post> result = service.updatePost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidUser(){
        Post post = makePost();
        post.setUserId(-5);
        Result<Post> result = service.updatePost(post);
        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteById(){
        Post post = makePost();
        Post expected = makePost();
        when(repository.add(post)).thenReturn(expected);
        Result<Post> result = service.addPost(post);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        when(repository.deleteById(post.getId())).thenReturn(true);
        boolean deletedResult = service.deletePost(post.getId());
        assertTrue(deletedResult);
    }

    Post makePost(){
        Post post = new Post();
        post.setId(1);
        post.setPostDate(LocalDate.now());
        post.setTitle("Pocket Monsters");
        post.setUserId(1);
        post.setContent("Prepare for trouble. Make it double.");

        return post;
    }
}
