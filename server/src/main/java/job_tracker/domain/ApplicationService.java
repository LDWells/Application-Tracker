package job_tracker.domain;

import job_tracker.data.CompanyRepository;
import job_tracker.data.JobRepository;
import job_tracker.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import job_tracker.data.ApplicationRepository;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, CompanyRepository companyRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
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

    public Result<ApplicationDTO> addWithDetails(ApplicationDTO applicationDTO) {
        Result<ApplicationDTO> result = validate(applicationDTO);
        if (!result.isSuccess()) {
            return result;
        }

        // Add or get existing company
        Company company = new Company(applicationDTO.getCompanyName(), applicationDTO.getCompanyAddress());
        Company storedCompany = companyRepository.add(company); //later on we can modify it to be addOrGet
        if (storedCompany == null) {
            result.addMessage("Company could not be added or retrieved.", ResultType.INVALID);
            return result;
        }
        applicationDTO.setCompanyId(storedCompany.getId());

        // Add job
        Job job = new Job(storedCompany.getId(), applicationDTO.getJobTitle(), applicationDTO.getJobDescription());
        Job storedJob = jobRepository.add(job);
        if (storedJob == null) {
            result.addMessage("Job could not be added.", ResultType.INVALID);
            return result;
        }
        applicationDTO.setJobId(storedJob.getId());

        // Add application
        Application application = new Application(applicationDTO.getUserId(), storedJob.getId(), applicationDTO.getApplicationDate(), applicationDTO.getAppliedOn(), Status.valueOf(applicationDTO.getStatus()));
        Application storedApplication = applicationRepository.add(application);
        if (storedApplication == null) {
            result.addMessage("Application could not be added.", ResultType.INVALID);
            return result;
        }
        applicationDTO.setApplicationId(storedApplication.getId());

        result.setPayload(applicationDTO);
        return result;
    }

    private Result<Application> validate(Application application) {
        Result<Application> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Application>> violations = validator.validate(application);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Application> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }

    private Result<ApplicationDTO> validate(ApplicationDTO applicationDTO) {
        Result<ApplicationDTO> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ApplicationDTO>> violations = validator.validate(applicationDTO);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<ApplicationDTO> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
