/************************************************************************
 * Author: Shawn Gibbons
 * Filename: Application.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

import java.time.LocalDate;

/************************************************************************
 * Class: Application
 *
 * Purpose:
 *      To handle and maintain application statuses and dates of
 *      applications.
 *
 * Manager functions:
 *      - Application()
 *      - Application(int id, int userId, int jobId, LocalDate applicationDate, Status status)
 *
 * Methods:
 *      - int getId()
 *      - void setId(int id)
 *      - int getUserId()
 *      - void setUserId(int userId)
 *      - int getJobId()
 *      - void setJobId(int jobId)
 *      - LocalDate getApplicationDate()
 *      - void setApplicationDate(LocalDate applicationDate)
 *      - Status getStatus()
 *      - void setStatus(Status status)
 *************************************************************************/
public class Application {
    private int id;
    private int userId;
    private int jobId;
    private LocalDate applicationDate;
    private Status status;

    public Application() {
    }

    public Application(int id, int userId, int jobId, LocalDate applicationDate, Status status) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.applicationDate = applicationDate;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
