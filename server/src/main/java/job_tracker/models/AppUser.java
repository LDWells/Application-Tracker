package job_tracker.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User
{

    private static final String AUTHORITY_PREFIX = "ROLE_";

    @NotNull
    @NotBlank(message = "User id is required")
    private int appUserId;

    @NotNull
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username cannot be longer than 50 characters")
    private String username;


    private String password;
    private boolean disabled;

    private List<String> roles = new ArrayList<>();



    public AppUser(int appUserId, String username, String password,
                   boolean disabled, List<String> roles) {
        super(username, password, !disabled,
                true, true, true,
                convertRolesToAuthorities(roles));
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.disabled = disabled;
        this.roles = roles;
    }

    public int getAppUserId()
    {
        return appUserId;
    }

    public void setAppUserId(int appUserId)
    {
        this.appUserId = appUserId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }

    public boolean hasRol(String role)
    {
        if (roles == null)
        {
            return false;
        }
        return roles.contains(role);
    }

    public static List<GrantedAuthority> convertRolesToAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String role : roles) {
            Assert.isTrue(!role.startsWith(AUTHORITY_PREFIX),
                    () ->
                            String.
                                    format("%s cannot start with %s (it is automatically added)",
                                            role, AUTHORITY_PREFIX));
            authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + role));
        }
        return authorities;
    }

    public static List<String> convertAuthoritiesToRoles(Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(a -> a.getAuthority().substring(AUTHORITY_PREFIX.length()))
                .collect(Collectors.toList());
    }

}
