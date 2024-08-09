package job_tracker.domain;

import job_tracker.data.TaskRepository;
import job_tracker.models.Status;
import job_tracker.models.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskServiceTest {
    @Autowired
    TaskService service;

    @MockBean
    TaskRepository repository;

    @Test
    void shouldFindByTaskId(){
        Task expected = makeTask();
        when(repository.findByTaskId(1)).thenReturn(expected);
        Task actual = service.findByTaskId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddWhenValid(){
        Task expected = makeTask();
        Task arg = makeTask();

        when(repository.add(arg)).thenReturn(expected);
        Result<Task> result = service.add(arg);
        assertNotNull(result.getPayload());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidDescription(){
        Task task = makeTask();
        task.setDescription(null);
        Result<Task> result = service.add(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidDueDate(){
        Task task = makeTask();
        task.setDueDate(null);
        Result<Task> result = service.add(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidReminderDate(){
        Task task = makeTask();
        task.setReminderDate(null);
        Result<Task> result = service.add(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidStatus(){
        Task task = makeTask();
        task.setStatus(null);
        Result<Task> result = service.add(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate(){
        Task task = makeTask();

        task.setStatus(Status.APPLIED);
        when(repository.update(task)).thenReturn(true);
        Result<Task> result = service.update(task);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidDescription(){
        Task task = makeTask();
        task.setDescription(null);
        Result<Task> result = service.update(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidDueDate(){
        Task task = makeTask();
        task.setDueDate(null);
        Result<Task> result = service.update(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidReminderDate(){
        Task task = makeTask();
        task.setReminderDate(LocalDate.of(2021, 1,1));
        Result<Task> result = service.update(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidStatus(){
        Task task = makeTask();
        task.setStatus(null);
        Result<Task> result = service.update(task);
        assertNull(result.getPayload());
    }

//    @Test
//    void shouldNotUpdateWhenInvalidId(){
//        Task task = makeTask();
//        task.setId(-5);
//        Result<Task> result = service.update(task);
//        assertNull(result.getPayload());
//    }

    @Test
    void shouldNotUpdateWhenInvalidApplicationId(){
        Task task = makeTask();
        task.setApplicationId(-5);
        Result<Task> result = service.update(task);
        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteById(){
        Task task = makeTask();
        Task expected = makeTask();
        when(repository.add(task)).thenReturn(expected);
        Result<Task> result = service.add(task);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        when(repository.deleteById(task.getId())).thenReturn(true);
        boolean deletedResult = service.delete(task.getId());
        assertTrue(deletedResult);
    }

    Task makeTask(){
        Task task = new Task();
        task.setId(1);
        task.setStatus(Status.INTERVIEW);
        task.setDescription("Catch all Unova Pokemon.");
        task.setApplicationId(1);
        task.setDueDate(LocalDate.of(2024, 12, 1));
        task.setReminderDate(LocalDate.of(2024, 11, 1));

        return task;
    }
}
