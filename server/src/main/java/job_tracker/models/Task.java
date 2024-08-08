package job_tracker.models;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Task {
//    @NotNull(message = "Task ID cannot be null")
//    @Positive(message = "Task ID cannot be negative")
    private int id;

    @NotNull(message = "Application ID cannot be null")
    @Positive(message = "Application ID cannot be negative")
    private int applicationId;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    @NotNull(message = "Due date cannot be null")
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

    @FutureOrPresent(message = "Reminder date cannot be in the past")
    private LocalDate reminderDate;

    @NotNull(message = "Status cannot be blank")
    private Status status;

    public Task() {
    }

    public Task(int id, int applicationId, String description, LocalDate dueDate, LocalDate reminderDate, Status status) {
        this.id = id;
        this.applicationId = applicationId;
        this.description = description;
        this.dueDate = dueDate;
        this.reminderDate = reminderDate;
        this.status = status;
    }

    public Task(int applicationId, String description, LocalDate dueDate, LocalDate reminderDate, Status status) {
        this.applicationId = applicationId;
        this.description = description;
        this.dueDate = dueDate;
        this.reminderDate = reminderDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}