package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class Comment {

    private int id;

    @NotNull(message = "Post ID cannot be null")
    @Positive(message = "Post ID cannot be negative")
    private int postId;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID cannot be negative")
    private int userId;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Comment date cannot be null")
    @PastOrPresent(message = "Comment date cannot be in future")
    private LocalDate commentDate;

    public Comment() {
    }

    public Comment(int id, int postId, int userId, String username, String content, LocalDate commentDate)
    {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.commentDate = commentDate;
    }

    public Comment(int postId, int userId, String username, String content, LocalDate commentDate)
    {
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.commentDate = commentDate;
    }

    public @NotNull(message = "Username cannot be null") String getUsername()
    {
        return username;
    }

    public void setUsername(@NotNull(message = "Username cannot be null") String username)
    {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }
}
