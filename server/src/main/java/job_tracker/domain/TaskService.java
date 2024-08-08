package job_tracker.domain;

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

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findByTaskId(int id) {
        return taskRepository.findByTaskId(id);
    }

    public List<Task> findByApplicationId(int id)
    {
        return taskRepository.findByApplicationId(id);
    }

    public Result<Task> add(Task task) {
        Result<Task> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Task> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        result.setPayload(taskRepository.add(task));
        return result;
    }

    public Result<Task> update(Task task) {
        Result<Task> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Task> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }
        taskRepository.update(task);
        result.setPayload(task);
        return result;
    }

    public boolean delete(int id) {
        return taskRepository.deleteById(id);
    }
}
