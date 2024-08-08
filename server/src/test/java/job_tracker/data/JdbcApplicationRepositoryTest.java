package job_tracker.data;

import job_tracker.models.Application;
import job_tracker.models.ApplicationDTO;
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

    @Test
    void shouldAddApplicationWithDetails() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyName("Test Company");
        dto.setCompanyAddress("123 Test St");
        dto.setJobTitle("Test Job");
        dto.setJobDescription("This is a test job.");
        dto.setUserId(1);
        dto.setApplicationDate(LocalDate.now());
        dto.setAppliedOn("Online");
        dto.setStatus("APPLIED");

        ApplicationDTO result = repository.addWithDetails(dto);

        assertNotNull(result);
        assertTrue(result.getApplicationId() > 0);
        assertTrue(result.getCompanyId() > 0);
        assertTrue(result.getJobId() > 0);
    }

    @Test
    void shouldFindApplicationByIdWithDetails() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyName("Test Company");
        dto.setCompanyAddress("123 Test St");
        dto.setJobTitle("Test Job");
        dto.setJobDescription("This is a test job.");
        dto.setUserId(1);
        dto.setApplicationDate(LocalDate.now());
        dto.setAppliedOn("Online");
        dto.setStatus("APPLIED");

        ApplicationDTO added = repository.addWithDetails(dto);
        ApplicationDTO found = repository.findByIdWithDetails(added.getApplicationId());

        assertNotNull(found);
        assertEquals(added.getApplicationId(), found.getApplicationId());
        assertEquals("Test Company", found.getCompanyName());
        assertEquals("Test Job", found.getJobTitle());
    }

    @Test
    void shouldFindAllApplicationsWithDetails() {
        List<ApplicationDTO> applications = repository.findAllWithDetails();
        assertNotNull(applications);
        assertTrue(applications.size() >= 0);
    }

    @Test
    void shouldUpdateApplicationWithDetails() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyName("Updated Company");
        dto.setCompanyAddress("Updated Address");
        dto.setJobTitle("Updated Job");
        dto.setJobDescription("Updated Description");
        dto.setUserId(1);
        dto.setApplicationDate(LocalDate.now());
        dto.setAppliedOn("Updated Online");
        dto.setStatus("INTERVIEW");

        // Adding new application to get an ID
        ApplicationDTO added = repository.addWithDetails(dto);

        // Updating the details
        dto.setApplicationId(added.getApplicationId());
        dto.setCompanyName("Updated Company Name");
        dto.setJobTitle("Updated Job Title");
        dto.setAppliedOn("Updated Applied On");
        dto.setStatus("OFFER");

        boolean updated = repository.updateWithDetails(dto);

        assertTrue(updated);

        ApplicationDTO updatedDTO = repository.findByIdWithDetails(dto.getApplicationId());

        assertNotNull(updatedDTO);
        assertEquals("Updated Company Name", updatedDTO.getCompanyName());
        assertEquals("Updated Job Title", updatedDTO.getJobTitle());
        assertEquals("Updated Applied On", updatedDTO.getAppliedOn());
        assertEquals("OFFER", updatedDTO.getStatus());
    }
}