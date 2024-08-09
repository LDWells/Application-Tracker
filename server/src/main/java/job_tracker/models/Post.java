package job_tracker.models;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Post {

    private int id;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID cannot be negative")
    private int userId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    @NotBlank(message = "Summary cannot be blank")
    @Size(max = 255, message = "Summary cannot be longer than 255 characters")
    private String summary;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Post date cannot be Null")
    @PastOrPresent(message = "Post date cannot be in future")
    private LocalDate postDate;

    public Post() {
    }


    public Post(int id, int userId, String title, String summary, String content, LocalDate postDate)
    {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.postDate = postDate;
    }

    public Post(int userId, String title, String summary, String content, LocalDate postDate)
    {
        this.userId = userId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.postDate = postDate;
    }

    public @NotBlank(message = "Summary cannot be blank") @Size(max = 255, message = "Summary cannot be longer than 255 characters") String getSummary()
    {
        return summary;
    }

    public void setSummary(@NotBlank(message = "Summary cannot be blank") @Size(max = 255, message = "Summary cannot be longer than 255 characters") String summary)
    {
        this.summary = summary;
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
