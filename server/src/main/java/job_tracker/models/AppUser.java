package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class AppUser
{
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

}
