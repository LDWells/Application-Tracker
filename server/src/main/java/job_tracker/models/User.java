/************************************************************************
 * Author: Shawn Gibbons
 * Filename: User.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

/************************************************************************
 * Class: User
 *
 * Purpose:
 *      To handle and maintain user information.
 *
 * Manager functions:
 *      - User()
 *      - User(int id, String username, String email, String password,
 *              Role role)
 *      - User(int id, String googleId, String username, String email,
 *              String password, Role role)
 *
 * Methods:
 *      - int getId()
 *      - void setId(int id)
 *      - String getGoogleId()
 *      - void setGoogleId(String googleId)
 *      - String getUsername()
 *      - void setUsername(String username)
 *      - String getEmail()
 *      - void setEmail(String email)
 *      - String getPassword()
 *      - void setPassword(String password)
 *      - Role getRole()
 *      - void setRole(Role role)
 *************************************************************************/
public class User {
    private int id;
    private String googleId;
    private String username;
    private String email;
    private String password;
    private Role role;

    public User() {
    }

    public User(int id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int id, String googleId, String username, String email, String password, Role role) {
        this.id = id;
        this.googleId = googleId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
