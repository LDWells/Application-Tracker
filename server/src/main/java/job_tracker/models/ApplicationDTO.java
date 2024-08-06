package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ApplicationDTO {

    private int companyId;
    @NotBlank
    private String companyName;
    private String companyAddress;

    private int jobId;
    @NotBlank
    private String jobTitle;
    private String jobDescription;

    private int applicationId;
    private int userId;
    @NotNull
    private LocalDate applicationDate;
    @NotBlank
    private String appliedOn;
    @NotBlank
    private String status;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public @NotBlank String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(@NotBlank String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public @NotBlank String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(@NotBlank String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public @NotNull LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(@NotNull LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public @NotBlank String getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(@NotBlank String appliedOn) {
        this.appliedOn = appliedOn;
    }

    public @NotBlank String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank String status) {
        this.status = status;
    }
}
