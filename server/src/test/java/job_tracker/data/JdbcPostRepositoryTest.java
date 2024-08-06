package job_tracker.data;

import job_tracker.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JdbcPostRepositoryTest {

    final static int NEXT_ID = 15;

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
        assertTrue(actual.size() >= 13 && actual.size() <= 15);

    }

    @Test
    void shouldAdd() {
        Post post = new Post(4, "Resume Writing Tips", "Test for resume", LocalDate.of(2024, 6, 17));
        Post actual = repository.add(post);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        Post actual = repository.findById(3);
        actual.setTitle("Test");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(5));
        assertFalse(repository.deleteById(5));
    }
}