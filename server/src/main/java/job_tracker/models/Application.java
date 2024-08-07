package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class Application {
    @NotNull(message = "Application ID cannot be null")
    @Positive(message = "Application ID cannot be negative")
    private int id;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID cannot be negative")
    private int userId;

    @NotNull(message = "Job ID cannot be null")
    @Positive(message = "Job ID cannot be negative")
    private int jobId;

    @PastOrPresent(message = "Application date cannot be in the future")
    private LocalDate applicationDate;

    @NotBlank(message = "Applied on cannot be blank")
    private String appliedOn;

    @NotNull(message = "Status cannot be null")
    private Status status;

    public Application() {
    }

    public Application(int id, int userId, int jobId, LocalDate applicationDate, String appliedOn, Status status) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.applicationDate = applicationDate;
        this.appliedOn = appliedOn;
        this.status = status;
    }

    public Application(int userId, int jobId, LocalDate applicationDate, String appliedOn, Status status) {
        this.userId = userId;
        this.jobId = jobId;
        this.applicationDate = applicationDate;
        this.appliedOn = appliedOn;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(String appliedOn) {
        this.appliedOn = appliedOn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
