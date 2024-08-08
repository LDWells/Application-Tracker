package job_tracker.data;

import job_tracker.models.Application;
import job_tracker.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcApplicationRepositoryTest {

    final static int NEXT_ID = 5;


    @Autowired
    JdbcApplicationRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        assertEquals("LinkedIn", repository.findById(1).getAppliedOn());
        assertEquals("REJECTED", repository.findById(4).getStatus().toString());
    }


    @Test
    void shouldFindAll() {
        List<Application> actual = repository.findAll();
        // can't predict order
        // if delete is first, we're down to 14
        // if add is first, we may go as high as 16
        assertTrue(actual.size() >= 2 && actual.size() <= 6);

    }

    @Test
    void shouldAdd() {
        Application application = new Application(3, 2, LocalDate.of(2023,1,13), "Indeed", Status.INTERVIEW);
        Application actual = repository.add(application);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        Application actual = repository.findById(3);
        actual.setAppliedOn("Indeed");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(4));
        assertFalse(repository.deleteById(4));
    }
}