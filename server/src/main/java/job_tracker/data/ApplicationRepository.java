package job_tracker.data;

import job_tracker.models.Application;
import job_tracker.models.ApplicationDTO;

import java.util.List;

public interface ApplicationRepository {
    Application findById(int id);
    List<Application> findAll();
    Application add(Application application);
    boolean update(Application application);
    boolean deleteById(int id);
    public ApplicationDTO findByIdWithDetails(int id);
    public List<ApplicationDTO> findAllWithDetails();
    }
