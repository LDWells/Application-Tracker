package job_tracker.data;

import job_tracker.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcCommentRepositoryTest {
    final static int NEXT_ID = 3;

    @Autowired
    JdbcCommentRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByCommentId() {
        Comment comment = repository.findByCommentId(2);
        assertNotNull(comment);
        assertEquals(2, comment.getId());
        assertEquals(2, comment.getUserId());
        assertEquals(LocalDate.of(2023, 3, 1), comment.getCommentDate());
    }


    @Test
    void shouldFindAll() {
        List<Comment> actual = repository.findAll();
        // can't predict order
        // if delete is first, we're down to 14
        // if add is first, we may go as high as 16
        assertTrue(actual.size() >= 1 && actual.size() <= 5);

    }

    @Test
    void shouldAdd() {
        Comment comment = new Comment(2,2, "john", "This application took WAY too long", LocalDate.of(2023,1,13) );
        Comment actual = repository.add(comment);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        Comment actual = repository.findByCommentId(1);
        actual.setCommentDate(LocalDate.of(2023,11,13));
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }
}