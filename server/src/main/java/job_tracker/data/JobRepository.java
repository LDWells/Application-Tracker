package job_tracker.data;

import job_tracker.models.Job;

import java.util.List;

public interface JobRepository {
    Job findById(int id);
    List<Job> findAll();
    Job add(Job job);
    boolean update(Job job);
    boolean deleteById(int id);
}