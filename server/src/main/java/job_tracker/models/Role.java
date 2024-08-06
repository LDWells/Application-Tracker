/************************************************************************
 * Author: Shawn Gibbons
 * Filename: Role.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

public enum Role {
    USER("User"),
    ADMIN("Admin");

    private final String name;

    Role(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
