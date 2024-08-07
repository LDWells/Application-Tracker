package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class Company {
    @NotNull(message = "Company ID cannot be null")
    @Positive(message = "Company ID cannot be negative")
    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;

    @Size(max = 255, message = "Address cannot be longer than 255 characters")
    private String address;

    public Company() {
    }

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
