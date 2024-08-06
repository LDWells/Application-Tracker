package job_tracker.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User {

    private static final String AUTHORITY_PREFIX = "ROLE_";

    private int appUserId;
    private String googleId;
    private String email;
    private String password;  // Add password field

    private List<String> roles = new ArrayList<>();

    public AppUser(int appUserId, String username, String password, boolean disabled, List<String> roles) {
        super(username, password, !disabled, true, true, true, convertRolesToAuthorities(roles));
        this.appUserId = appUserId;
        this.password = password;  // Set password in constructor
    }

    public AppUser(int appUserId, String googleId, String username, String password, boolean disabled, List<String> roles) {
        super(username, password, !disabled, true, true, true, convertRolesToAuthorities(roles));
        this.appUserId = appUserId;
        this.googleId = googleId;
        this.password = password;  // Set password in constructor
    }


    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Override getPassword method to satisfy the parent class's requirement
    @Override
    public String getPassword() {
        return password;  // Return password
    }

    // Add setPassword method
    public void setPassword(String password) {
        this.password = password;
    }

    public static List<GrantedAuthority> convertRolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(AUTHORITY_PREFIX + role))
                .collect(Collectors.toList());
    }

    public static List<String> convertAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> authority.getAuthority().substring(AUTHORITY_PREFIX.length()))
                .collect(Collectors.toList());
    }
}
