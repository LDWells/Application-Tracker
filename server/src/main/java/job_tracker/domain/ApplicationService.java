package job_tracker.domain;

import job_tracker.models.ApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import job_tracker.data.ApplicationRepository;
import job_tracker.models.Application;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final Validator validator;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, Validator validator) {
        this.applicationRepository = applicationRepository;
        this.validator = validator;
    }

    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }

    public Application findApplicationById(int id) {
        return applicationRepository.findById(id);
    }

    public Application addApplication(@Valid Application application) {
        return applicationRepository.add(application);
    }

    public boolean updateApplication(@Valid Application application) {
        return applicationRepository.update(application);
    }

    public boolean deleteApplicationById(int id) {
        return applicationRepository.deleteById(id);
    }

    public List<ApplicationDTO> findAllApplicationsWithDetails() {
        return applicationRepository.findAllWithDetails();
    }

    public ApplicationDTO findApplicationByIdWithDetails(int id) {
        return applicationRepository.findByIdWithDetails(id);
    }
}
