/************************************************************************
 * Author: Shawn Gibbons
 * Filename: Comment.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/************************************************************************
 * Class: Comment
 *
 * Purpose:
 *      To handle and maintain comment content and dates of comments
 *
 * Manager functions:
 *      - Comment()
 *      - Comment(int id, int postId, int userId, String content,
 *                  LocalDate commentDate)
 *
 * Methods:
 *      - int getId()
 *      - void setId(int id)
 *      - int getPostId()
 *      - void setPostId(int postId)
 *      - int getUserId()
 *      - void setUserId(int userId)
 *      - String getContent()
 *      - void setContent(String content)
 *      - LocalDate getCommentDate()
 *      - void setCommentDate(LocalDate commentDate)
 *
 *************************************************************************/
public class Comment {
    private int id;

    @NotNull(message = "Post ID cannot be null")
    private int postId;

    @NotNull(message = "User ID cannot be null")
    private int userId;

    @NotBlank(message = "Content cannot be blank")
    private String content;
    private LocalDate commentDate;

    public Comment() {
    }

    public Comment(int id, int postId, int userId, String content, LocalDate commentDate) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.commentDate = commentDate;
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
