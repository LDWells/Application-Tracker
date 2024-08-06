/************************************************************************
 * Author: Shawn Gibbons
 * Filename: Post.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/************************************************************************
 * Class: Post
 *
 * Purpose:
 *      To handle and maintain post information.
 *
 * Manager functions:
 *      - Post()
 *      - Post(int id, int userId, String title, String content, LocalDate postDate)
 *
 * Methods:
 *      - int getId()
 *      - void setId(int id)
 *      - int getUserId()
 *      - void setUserId(int userId)
 *      - String getTitle()
 *      - void setTitle(String title)
 *      - String getContent()
 *      - void setContent(String content)
 *      - LocalDate getPostDate()
 *      - void setPostDate(LocalDate postDate)
 *************************************************************************/
public class Post {
    private int id;

    @NotNull(message = "User ID cannot be null")
    private int userId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private LocalDate postDate;

    public Post() {
    }

    public Post(int id, int userId, String title, String content, LocalDate postDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
    }

    public Post(int userId, String title, String content, LocalDate postDate) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }
}
