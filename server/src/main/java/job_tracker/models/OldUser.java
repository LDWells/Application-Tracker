/************************************************************************
 * Author: Shawn Gibbons
 * Filename: User.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 * 8/6/2024 - Modified the model to work with AppUser - Ahmad Mashal
 ************************************************************************/

package job_tracker.models;

//import javax.persistence.*;
import java.util.Objects;

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

//@Entity
//@Table(name = "User")
public class OldUser
{

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(unique = true)
    private String googleId;

    //@Column(nullable = false, unique = true)
    private String username;

    //@Column(nullable = false, unique = true)
    private String email;

    //@Column(nullable = false)
    private String password;

    //@Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private Role role;

    public OldUser() {
    }

    public OldUser(int id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public OldUser(int id, String googleId, String username, String email, String password, Role role) {
        this.id = id;
        this.googleId = googleId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public OldUser(String googleId, String username, String email, String password, Role role) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OldUser oldUser = (OldUser) o;
        return id == oldUser.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
