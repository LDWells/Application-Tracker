package job_tracker.domain;

import job_tracker.data.JobRepository;
import job_tracker.models.Application;
import job_tracker.models.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JobServiceTest {
    @Autowired
    JobService service;

    @MockBean
    JobRepository repository;

    @Test
    void shouldFindById(){
        Job expected = makeJob();
        when(repository.findById(1)).thenReturn(expected);
        Job actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddWhenValid(){
        Job expected = makeJob();
        Job arg = makeJob();

        when(repository.add(arg)).thenReturn(expected);
        Result<Job> result = service.add(arg);
        assertNotNull(result.getPayload());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidTitle(){
        Job job = makeJob();
        job.setTitle(null);
        Result<Job> result = service.add(job);
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate(){
        Job job = makeJob();

        job.setDescription("Testing Job");
        when(repository.update(job)).thenReturn(true);
        Result<Job> result = service.update(job);
        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidTitle(){
        Job job = makeJob();
        job.setTitle(null);
        Result<Job> result = service.update(job);
        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteById(){
        Job job = makeJob();
        Job expected = makeJob();
        when(repository.add(job)).thenReturn(expected);
        Result<Job> result = service.add(job);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        when(repository.deleteById(job.getId())).thenReturn(true);
        boolean deletedResult = service.deleteById(job.getId());
        assertTrue(deletedResult);
    }

    Job makeJob(){
        Job job = new Job();
        job.setId(1);
        job.setTitle("Pokemon Trainer");
        job.setCompanyId(1);
        job.setDescription("Got to catch them all.");

        return job;
    }
}
