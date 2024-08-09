package job_tracker.domain;

import job_tracker.data.ApplicationRepository;
import job_tracker.data.CompanyRepository;
import job_tracker.data.JobRepository;
import job_tracker.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private ApplicationService applicationService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldAddWhenValid(){
        Application expected = makeApplication();
        Application arg = makeApplication();

        when(applicationRepository.add(arg)).thenReturn(expected);
        Result<Application> result = applicationService.add(arg);
        assertNotNull(result.getPayload());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidDate(){
        Application application = makeApplication();
        application.setApplicationDate(LocalDate.of(2025, 1, 1));
        Result<Application> result = applicationService.add(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidUserID(){
        Application application = makeApplication();
        application.setUserId(-5);
        Result<Application> result = applicationService.add(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidApplicationID(){
        Application application = makeApplication();
        application.setId(-5);
        Result<Application> result = applicationService.add(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidJobID(){
        Application application = makeApplication();
        application.setJobId(-5);
        Result<Application> result = applicationService.add(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenBlankAppliedOn(){
        Application application = makeApplication();
        application.setAppliedOn(null);
        Result<Application> result = applicationService.add(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidStatus(){
        Application application = makeApplication();
        application.setStatus(null);
        Result<Application> result = applicationService.add(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldFindById(){
        Application expected = makeApplication();
        when(applicationRepository.findById(1)).thenReturn(expected);
        Application actual = applicationService.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByInvalidId(){
        Application expected = makeApplication();
        when(applicationRepository.findById(1)).thenReturn(expected);
        Application actual = applicationService.findById(10);
        assertNotEquals(expected, actual);
    }

    @Test
    void shouldUpdate(){
        Application application = makeApplication();

        application.setStatus(Status.PENDING);
        when(applicationRepository.update(application)).thenReturn(true);
        Result<Application> result = applicationService.update(application);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidApplicationID(){
        Application application = makeApplication();

        application.setId(-5);
        Result<Application> result = applicationService.update(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidUserID(){
        Application application = makeApplication();

        application.setUserId(-5);
        Result<Application> result = applicationService.update(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidJobID(){
        Application application = makeApplication();

        application.setJobId(-5);
        Result<Application> result = applicationService.update(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenBlankAppliedOn(){
        Application application = makeApplication();

        application.setAppliedOn(null);
        Result<Application> result = applicationService.update(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidStatus(){
        Application application = makeApplication();

        application.setStatus(null);
        Result<Application> result = applicationService.update(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldDeleteById(){
        Application application = makeApplication();
        Application expected = makeApplication();
        when(applicationRepository.add(application)).thenReturn(expected);
        Result<Application> result = applicationService.add(application);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        when(applicationRepository.deleteById(application.getId())).thenReturn(true);
        boolean deletedResult = applicationService.deleteById(application.getId());
        assertTrue(deletedResult);
    }

    @Test
    void shouldNotDeleteByInvalidId(){
        Application application = makeApplication();
        Application expected = makeApplication();
        when(applicationRepository.add(application)).thenReturn(expected);
        Result<Application> result = applicationService.add(application);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        application.setId(-5);
        boolean deletedResult = applicationService.deleteById(application.getId());
        assertFalse(deletedResult);
    }

    Application makeApplication(){
        Application app = new Application();
        app.setId(1);
        app.setApplicationDate(LocalDate.now());
        app.setAppliedOn("Indeed");
        app.setStatus(Status.APPLIED);
        app.setJobId(2);
        app.setUserId(3);

        return app;
    }

    @Test
    void shouldUpdateWithDetails() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyId(1);
        dto.setCompanyName("Updated Company");
        dto.setCompanyAddress("Updated Address");
        dto.setJobId(1);
        dto.setJobTitle("Updated Job");
        dto.setJobDescription("Updated Description");
        dto.setApplicationId(1);
        dto.setUserId(1);
        dto.setApplicationDate(LocalDate.now());
        dto.setAppliedOn("Updated Online");
        dto.setStatus("INTERVIEW");

        when(companyRepository.update(any(Company.class))).thenReturn(true);
        when(jobRepository.update(any(Job.class))).thenReturn(true);
        when(applicationRepository.update(any(Application.class))).thenReturn(true);

        Result<ApplicationDTO> result = applicationService.updateWithDetails(dto);

        assertTrue(result.isSuccess());
        assertEquals(dto, result.getPayload());

        verify(companyRepository).update(any(Company.class));
        verify(jobRepository).update(any(Job.class));
        verify(applicationRepository).update(any(Application.class));
    }

    @Test
    void shouldFailToUpdateWithDetailsWhenCompanyUpdateFails() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyId(1);
        dto.setCompanyName("Updated Company");
        dto.setCompanyAddress("Updated Address");
        dto.setJobId(1);
        dto.setJobTitle("Updated Job");
        dto.setJobDescription("Updated Description");
        dto.setApplicationId(1);
        dto.setUserId(1);
        dto.setApplicationDate(LocalDate.now());
        dto.setAppliedOn("Updated Online");
        dto.setStatus("INTERVIEW");

        when(companyRepository.update(any(Company.class))).thenReturn(false);

        Result<ApplicationDTO> result = applicationService.updateWithDetails(dto);

        assertFalse(result.isSuccess());
        assertEquals("Company could not be updated.", result.getMessages().get(0));
    }

    @Test
    void shouldFailToUpdateWithDetailsWhenJobUpdateFails() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyId(1);
        dto.setCompanyName("Updated Company");
        dto.setCompanyAddress("Updated Address");
        dto.setJobId(1);
        dto.setJobTitle("Updated Job");
        dto.setJobDescription("Updated Description");
        dto.setApplicationId(1);
        dto.setUserId(1);
        dto.setApplicationDate(LocalDate.now());
        dto.setAppliedOn("Updated Online");
        dto.setStatus("INTERVIEW");

        when(companyRepository.update(any(Company.class))).thenReturn(true);
        when(jobRepository.update(any(Job.class))).thenReturn(false);

        Result<ApplicationDTO> result = applicationService.updateWithDetails(dto);

        assertFalse(result.isSuccess());
        assertEquals("Company could not be updated.", result.getMessages().get(0));
    }

    @Test
    void shouldFailToUpdateWithDetailsWhenApplicationUpdateFails() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyId(1);
        dto.setCompanyName("Updated Company");
        dto.setCompanyAddress("Updated Address");
        dto.setJobId(1);
        dto.setJobTitle("Updated Job");
        dto.setJobDescription("Updated Description");
        dto.setApplicationId(1);
        dto.setUserId(1);
        dto.setApplicationDate(LocalDate.now());
        dto.setAppliedOn("Updated Online");
        dto.setStatus("INTERVIEW");

        when(companyRepository.update(any(Company.class))).thenReturn(true);
        when(jobRepository.update(any(Job.class))).thenReturn(true);
        when(applicationRepository.update(any(Application.class))).thenReturn(false);

        Result<ApplicationDTO> result = applicationService.updateWithDetails(dto);

        assertFalse(result.isSuccess());
        assertEquals("Application could not be updated.", result.getMessages().get(0));
    }

}
