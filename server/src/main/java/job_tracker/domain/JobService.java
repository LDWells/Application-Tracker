package job_tracker.domain;

import job_tracker.data.JobRepository;
import job_tracker.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final Validator validator;

    @Autowired
    public JobService(JobRepository jobRepository, Validator validator) {
        this.jobRepository = jobRepository;
        this.validator = validator;
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public Job findById(int id) {
        return jobRepository.findById(id);
    }

    public Result<Job> add(Job job) {
        Result<Job> result = validate(job);
        if (!result.isSuccess()) {
            return result;
        }

        Job createdJob = jobRepository.add(job);
        result.setPayload(createdJob);
        return result;
    }

    public Result<Job> update(Job job) {
        Result<Job> result = validate(job);
        if (!result.isSuccess()) {
            return result;
        }

        boolean success = jobRepository.update(job);
        if (!success) {
            result.addMessage("Job not found.", ResultType.NOT_FOUND);
        }

        result.setPayload(job);
        return result;
    }
    public Result<Job> create(Job job) {
        Result<Job> result = validate(job);
        if (!result.isSuccess()) {
            return result;
        }

        Job createdJob = jobRepository.add(job);
        result.setPayload(createdJob);
        return result;
    }

    public boolean deleteById(int id) {
        return jobRepository.deleteById(id);
    }

    private Result<Job> validate(Job job) {
        Result<Job> result = new Result<>();
        Set<ConstraintViolation<Job>> violations = validator.validate(job);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Job> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
