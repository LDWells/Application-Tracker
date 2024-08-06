package job_tracker.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.JobRepository;
import job_tracker.models.Job;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final Validator validator;

    @Autowired
    public JobService(JobRepository jobRepository, Validator validator) {
        this.jobRepository = jobRepository;
        this.validator = validator;
    }

    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

    public Job findJobById(int id) {
        return jobRepository.findById(id);
    }

    public Job addJob(@Valid Job job) {
        return jobRepository.add(job);
    }

    public boolean updateJob(@Valid Job job) {
        return jobRepository.update(job);
    }

    public boolean deleteJobById(int id) {
        return jobRepository.deleteById(id);
    }
}
