package job_tracker.data;

import job_tracker.models.Task;

import java.util.List;

public interface TaskRepository {
    Task findById(int id);
    List<Task> findAll();
    Task add(Task task);
    boolean update(Task task);
    boolean deleteById(int id);
}
