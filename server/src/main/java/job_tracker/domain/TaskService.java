package job_tracker.domain;

import job_tracker.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.TaskRepository;
import job_tracker.models.Task;

import javax.validation.*;
import java.util.List;
import java.util.Set;

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

    public Result<Task> addTask(Task task) {
        Result<Task> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Task> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }

        result.setPayload(taskRepository.add(task));
        return result;
    }

    public Result<Task> updateTask(Task task) {
        Result<Task> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Task> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }
        taskRepository.update(task);
        result.setPayload(task);
        return result;
    }

    public boolean deleteTask(int id) {
        return taskRepository.deleteById(id);
    }
}
