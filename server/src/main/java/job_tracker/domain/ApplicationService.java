package job_tracker.domain;

import job_tracker.models.ApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import job_tracker.data.ApplicationRepository;
import job_tracker.models.Application;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Application findById(int id) {
        return applicationRepository.findById(id);
    }

    public Result<Application> add(Application application) {
        Result<Application> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Application>> violations = validator.validate(application);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Application> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        result.setPayload(applicationRepository.add(application));
        return result;
    }

    public Result<Application> update(Application application) {
        Result<Application> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Application>> violations = validator.validate(application);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Application> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        applicationRepository.update(application);
        result.setPayload(application);

        return result;
    }

    public boolean deleteById(int id) {
        return applicationRepository.deleteById(id);
    }

    public List<ApplicationDTO> findAllApplicationsWithDetails() {
        return applicationRepository.findAllWithDetails();
    }

    public ApplicationDTO findApplicationByIdWithDetails(int id) {
        return applicationRepository.findByIdWithDetails(id);
    }
}
