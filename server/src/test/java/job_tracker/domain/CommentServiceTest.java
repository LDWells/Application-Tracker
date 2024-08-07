package job_tracker.domain;

import job_tracker.data.CommentRepository;
import job_tracker.models.Application;
import job_tracker.models.Comment;
import job_tracker.models.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService service;

    @MockBean
    CommentRepository repository;

    @Test
    void shouldFindById(){
        Comment expected = makeComment();
        when(repository.findById(1)).thenReturn(expected);
        Comment actual = service.findCommentById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByInvalidId(){
        Comment expected = makeComment();
        when(repository.findById(1)).thenReturn(expected);
        Comment actual = service.findCommentById(-5);
        assertNotEquals(expected, actual);
    }

    @Test
    void shouldAddWhenValid(){
        Comment expected = makeComment();
        Comment arg = makeComment();

        when(repository.add(arg)).thenReturn(expected);
        Result<Comment> result = service.addComment(arg);
        assertNotNull(result.getPayload());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidContent(){
        Comment comment = makeComment();
        comment.setContent(null);
        Result<Comment> result = service.addComment(comment);

        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidCommentDate(){
        Comment comment = makeComment();
        comment.setCommentDate(null);
        Result<Comment> result = service.addComment(comment);

        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate(){
        Comment comment = makeComment();

        comment.setContent("Testing update");
        when(repository.update(comment)).thenReturn(true);
        Result<Comment> result = service.updateComment(comment);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidContent(){
        Comment comment = makeComment();
        comment.setContent(null);
        Result<Comment> result = service.updateComment(comment);

        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidCommentDate(){
        Comment comment = makeComment();
        comment.setCommentDate(null);
        Result<Comment> result = service.updateComment(comment);

        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteById(){
        Comment comment = makeComment();
        Comment expected = makeComment();
        when(repository.add(comment)).thenReturn(expected);
        Result<Comment> result = service.addComment(comment);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        when(repository.deleteById(comment.getId())).thenReturn(true);
        boolean deletedResult = service.deleteComment(comment.getId());
        assertTrue(deletedResult);
    }

    Comment makeComment(){
        Comment comment = new Comment();
        comment.setId(1);
        comment.setUserId(2);
        comment.setPostId(3);
        comment.setCommentDate(LocalDate.now());
        comment.setContent("Testing comments");

        return comment;
    }
}
