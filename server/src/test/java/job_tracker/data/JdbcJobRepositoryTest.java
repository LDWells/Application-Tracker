package job_tracker.data;

import job_tracker.models.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JdbcJobRepositoryTest {

    final static int NEXT_ID = 16;

    @Autowired
    JdbcJobRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        assertEquals("Software Engineer", repository.findById(1).getTitle());
        assertEquals("Business Analyst", repository.findById(2).getTitle());
    }


    @Test
    void shouldFindAll() {
        List<Job> actual = repository.findAll();
        // can't predict order
        // if delete is first, we're down to 14
        // if add is first, we may go as high as 16
        assertTrue(actual.size() >= 14 && actual.size() <= 16);

    }

    @Test
    void shouldAdd() {
        Job job = new Job(5, "Tutor", "Help students.");
        Job actual = repository.add(job);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        Job actual = repository.findById(3);
        actual.setTitle("Test");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(5));
        assertFalse(repository.deleteById(5));
    }
}