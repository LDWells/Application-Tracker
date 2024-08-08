package job_tracker.controllers;

import job_tracker.domain.Result;
import job_tracker.domain.TaskService;
import job_tracker.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findByTaskId(@PathVariable int id) {
        Task task = taskService.findByTaskId(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<List<Task>> findByApplicationId(@PathVariable int id) {
        List<Task> tasks = taskService.findByApplicationId(id);
        if (tasks == null || tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody Task task) {
        Result<Task> result = taskService.add(task);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody Task task) {
        if (id != task.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Task> result = taskService.update(task);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        if (taskService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
