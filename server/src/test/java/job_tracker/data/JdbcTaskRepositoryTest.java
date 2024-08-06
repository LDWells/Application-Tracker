package job_tracker.data;

import job_tracker.models.Status;
import job_tracker.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JdbcTaskRepositoryTest {

    final static int NEXT_ID = 16;

    @Autowired
    JdbcTaskRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        assertEquals("Follow up email", repository.findById(1).getDescription());
        assertEquals("Prepare for interview", repository.findById(2).getDescription());
    }


    @Test
    void shouldFindAll() {
        List<Task> actual = repository.findAll();
        // can't predict order
        // if delete is first, we're down to 14
        // if add is first, we may go as high as 16
        assertTrue(actual.size() >= 14 && actual.size() <= 16);

    }

    @Test
    void shouldAdd() {
        Task task = new Task(11, "Schedule mock interview",
                LocalDate.of(2023, 11, 11),  LocalDate.of(2023, 11, 1),
                Status.PENDING);
        Task actual = repository.add(task);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        Task actual = repository.findById(3);
        actual.setDescription("Test");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(15));
        assertFalse(repository.deleteById(15));
    }
}