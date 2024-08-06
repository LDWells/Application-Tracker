package job_tracker.data;

import job_tracker.models.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JdbcCompanyRepositoryTest {

    final static int NEXT_ID = 16;


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
        // if delete is first, we're down to 14
        // if add is first, we may go as high as 16
        assertTrue(actual.size() >= 14 && actual.size() <= 16);

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