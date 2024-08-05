/************************************************************************
 * Author: Shawn Gibbons
 * Filename: Job.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

/************************************************************************
 * Class: Job
 *
 * Purpose:
 *      To handle and maintain Job information such as title and
 *      description.
 *
 * Manager functions:
 *      - Job()
 *      - Job(int id, int companyId, String title, String description)
 *
 * Methods:
 *     - int getId()
 *     - void setId(int id)
 *     - int getCompanyId()
 *     - void setCompanyId(int companyId)
 *     - String getTitle()
 *     - void setTitle(String title)
 *     - String getDescription()
 *     - void setDescription(String description)
 *************************************************************************/
public class Job {
    private int id;
    private int companyId;
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
