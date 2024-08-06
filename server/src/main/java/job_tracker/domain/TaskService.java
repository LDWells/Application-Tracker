package job_tracker.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.TaskRepository;
import job_tracker.models.Task;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final Validator validator;

    @Autowired
    public TaskService(TaskRepository taskRepository, Validator validator) {
        this.taskRepository = taskRepository;
        this.validator = validator;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(int id) {
        return taskRepository.findById(id);
    }

    public Task addTask(@Valid Task task) {
        return taskRepository.add(task);
    }

    public boolean updateTask(@Valid Task task) {
        return taskRepository.update(task);
    }

    public boolean deleteTask(int id) {
        return taskRepository.deleteById(id);
    }
}
