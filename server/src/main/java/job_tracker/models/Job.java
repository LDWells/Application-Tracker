package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

public class Job {
    @NotNull(message = "Job ID cannot be null")
    @Positive(message = "Job ID cannot be negative")
    private int id;

    @NotNull(message = "Company ID cannot be null")
    @Positive(message = "Company ID cannot be negative")
    private int companyId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    private String title;

    private String description;

    public Job() {
    }

    public Job(int id, int companyId, String title, String description) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.description = description;
    }

    public Job(int companyId, String title, String description) {
        this.companyId = companyId;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
