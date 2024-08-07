package job_tracker.domain;

import job_tracker.App;
import job_tracker.data.ApplicationRepository;
import job_tracker.models.Application;
import job_tracker.models.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicationServiceTest {

    @Autowired
    ApplicationService service;

    @MockBean
    ApplicationRepository repository;

    @Test
    void shouldAddWhenValid(){
        Application expected = makeApplication();
        Application arg = makeApplication();

        when(repository.add(arg)).thenReturn(expected);
        Result<Application> result = service.addApplication(arg);
        assertNotNull(result.getPayload());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidDate(){
        Application application = makeApplication();
        application.setApplicationDate(LocalDate.of(2025, 1, 1));
        Result<Application> result = service.addApplication(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidUserID(){
        Application application = makeApplication();
        application.setUserId(-5);
        Result<Application> result = service.addApplication(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidApplicationID(){
        Application application = makeApplication();
        application.setId(-5);
        Result<Application> result = service.addApplication(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidJobID(){
        Application application = makeApplication();
        application.setJobId(-5);
        Result<Application> result = service.addApplication(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenBlankAppliedOn(){
        Application application = makeApplication();
        application.setAppliedOn(null);
        Result<Application> result = service.addApplication(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotAddWhenInvalidStatus(){
        Application application = makeApplication();
        application.setStatus(null);
        Result<Application> result = service.addApplication(application);
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldFindById(){
        Application expected = makeApplication();
        when(repository.findById(1)).thenReturn(expected);
        Application actual = service.findApplicationById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByInvalidId(){
        Application expected = makeApplication();
        when(repository.findById(1)).thenReturn(expected);
        Application actual = service.findApplicationById(10);
        assertNotEquals(expected, actual);
    }

    @Test
    void shouldUpdate(){
        Application application = makeApplication();

        application.setStatus(Status.PENDING);
        when(repository.update(application)).thenReturn(true);
        Result<Application> result = service.updateApplication(application);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidApplicationID(){
        Application application = makeApplication();

        application.setId(-5);
        Result<Application> result = service.updateApplication(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidUserID(){
        Application application = makeApplication();

        application.setUserId(-5);
        Result<Application> result = service.updateApplication(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidJobID(){
        Application application = makeApplication();

        application.setJobId(-5);
        Result<Application> result = service.updateApplication(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenBlankAppliedOn(){
        Application application = makeApplication();

        application.setAppliedOn(null);
        Result<Application> result = service.updateApplication(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidStatus(){
        Application application = makeApplication();

        application.setStatus(null);
        Result<Application> result = service.updateApplication(application);

        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldDeleteById(){
        Application application = makeApplication();
        Application expected = makeApplication();
        when(repository.add(application)).thenReturn(expected);
        Result<Application> result = service.addApplication(application);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        when(repository.deleteById(application.getId())).thenReturn(true);
        boolean deletedResult = service.deleteApplicationById(application.getId());
        assertTrue(deletedResult);
    }

    @Test
    void shouldNotDeleteByInvalidId(){
        Application application = makeApplication();
        Application expected = makeApplication();
        when(repository.add(application)).thenReturn(expected);
        Result<Application> result = service.addApplication(application);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        application.setId(-5);
        boolean deletedResult = service.deleteApplicationById(application.getId());
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
}
