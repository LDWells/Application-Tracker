package job_tracker.data;

import job_tracker.models.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcCompanyRepositoryTest {

    final static int NEXT_ID = 18;


    @Autowired
    JdbcCompanyRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        assertEquals("Tech Corp", repository.findById(1).getName());
        assertEquals("Finance Group", repository.findById(4).getName());
    }


    @Test
    void shouldFindAll() {
        List<Company> actual = repository.findAll();
        // can't predict order
        assertTrue(actual.size() >= 12 && actual.size() <= 18);

    }

    @Test
    void shouldAdd() {
        Company company = new Company("Lockheed Martin", "123 Main Test Rd");
        Company actual = repository.add(company);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getId());
    }

    @Test
    void shouldUpdate() {
        Company actual = repository.findById(3);
        actual.setName("Trimble");
        assertTrue(repository.update(actual));

    }


    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(5));
        assertFalse(repository.deleteById(5));
    }
}