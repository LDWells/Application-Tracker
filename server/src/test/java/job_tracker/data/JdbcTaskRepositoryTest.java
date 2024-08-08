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

@SpringBootTest
class JdbcTaskRepositoryTest {

    final static int NEXT_ID = 4;

    @Autowired
    JdbcTaskRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByTaskId() {
        assertEquals("Follow up email", repository.findByTaskId(1).getDescription());
        assertEquals("Prepare for interview", repository.findByTaskId(2).getDescription());
    }


    @Test
    void shouldFindAll() {
        List<Task> actual = repository.findAll();
        // can't predict order
        // if delete is first, we're down to 14
        // if add is first, we may go as high as 16
        System.out.println(actual.size());
        assertTrue(actual.size() >= 1 && actual.size() <= 5);

    }

    @Test
    void shouldAdd() {
        Task task = new Task(2, "Schedule mock interview",
                LocalDate.of(2023, 11, 11),  LocalDate.of(2023, 11, 1),
                Status.PENDING);
        Task actual = repository.add(task);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        Task actual = repository.findByTaskId(3);
        actual.setDescription("Test");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }
}