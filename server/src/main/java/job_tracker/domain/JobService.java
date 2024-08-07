package job_tracker.domain;

import job_tracker.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.JobRepository;
import job_tracker.models.Job;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

    public Job findJobById(int id) {
        return jobRepository.findById(id);
    }

    public Result<Job> addJob(Job job) {
        Result<Job> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Job>> violations = validator.validate(job);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Job> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }

        result.setPayload(jobRepository.add(job));
        return result;
    }

    public Result<Job> updateJob(Job job) {
        Result<Job> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Job>> violations = validator.validate(job);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Job> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }
        jobRepository.update(job);
        result.setPayload(job);
        return result;
    }

    public boolean deleteJobById(int id) {
        return jobRepository.deleteById(id);
    }
}
