package job_tracker.data;

import job_tracker.models.Role;
import job_tracker.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JdbcUserRepositoryTest {

    final static int NEXT_ID = 16;


    @Autowired
    JdbcUserRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
            knownGoodState.set();
        }


    @Test
    void shouldFindById() {
        assertEquals("admin_user", repository.findById(1).getUsername());
        assertEquals("john_doe", repository.findById(2).getUsername());
    }


    @Test
    void shouldFindAll() {
        List<User> actual = repository.findAll();
        // can't predict order
        // if delete is first, we're down to 14
        // if add is first, we may go as high as 16
        assertTrue(actual.size() >= 14 && actual.size() <= 16);

    }

    @Test
    void shouldAdd() {
        User user = new User("google-789", "test", "test@example.com", "password123", Role.USER);
        User actual = repository.add(user);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        User actual = repository.findById(3);
        actual.setUsername("Test_Mctest");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(5));
        assertFalse(repository.deleteById(5));
    }
}