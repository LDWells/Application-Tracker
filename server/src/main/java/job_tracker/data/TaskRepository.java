package job_tracker.data;

import job_tracker.models.Task;

import java.util.List;

public interface TaskRepository {
    Task findByTaskId(int id);
    List<Task> findAll();
    List<Task> findByApplicationId(int id);
    Task add(Task task);
    boolean update(Task task);
    boolean deleteById(int id);
}
