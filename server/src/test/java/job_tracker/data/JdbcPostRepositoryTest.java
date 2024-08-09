package job_tracker.data;

import job_tracker.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcPostRepositoryTest {

    final static int NEXT_ID = 4;

    @Autowired
    JdbcPostRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        assertEquals("My Interview Experience at Tech Corp", repository.findById(1).getTitle());
        assertEquals("Tips for Job Applications", repository.findById(2).getTitle());
    }


    @Test
    void shouldFindAll() {
        List<Post> actual = repository.findAll();
        // can't predict order
        // if delete is first, we're down to 13
        // if add is first, we may go as high as 15
        assertTrue(actual.size() >= 1 && actual.size() <= 5);

    }

    @Test
    void shouldAdd() {
        Post post = new Post(2, "Resume Writing Tips", "Test for resume", "Test content", LocalDate.of(2024, 6, 17));
        Post actual = repository.add(post);
        assertNotNull(actual);
        assertTrue(actual.getId() >= 3);
    }

    @Test
    void shouldUpdate() {
        Post actual = repository.findById(2);
        actual.setTitle("Test");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }
}