/************************************************************************
 * Author: Shawn Gibbons
 * Filename: Task.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

/************************************************************************
 * Class: Task
 *
 * Purpose:
 *      To handle and maintain task information.
 *
 * Manager functions:
 *      - TasK()
 *      - Task(int id, int applicationId, String description,
 *              LocalDate dueDate, LocalDate reminderDate, Status status)
 *
 * Methods:
 *      - int getId()
 *      - void setId(int id)
 *      - int getApplicationId()
 *      - void setApplicationId(int applicationId)
 *      - String getDescription()
 *      - void setDescription(String description)
 *      - LocalDate getDueDate()
 *      - void setDueDate(LocalDate dueDate)
 *      - LocalDate getReminderDate()
 *      - void setReminderDate(LocalDate reminderDate)
 *      - Status getStatus()
 *      - void setStatus(Status status)
 *************************************************************************/
public class Task {
    private int id;

    @NotNull(message = "Application ID cannot be null")
    private int applicationId;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

    @FutureOrPresent(message = "Reminder date cannot be in the past")
    private LocalDate reminderDate;

    @NotBlank(message = "Status cannot be blank")
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