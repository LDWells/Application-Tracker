/************************************************************************
 * Author: Shawn Gibbons
 * Filename: Company.java
 * Date Created: 8/5/2024
 * Modifications: 8/5/2024 - created and finished model - Shawn Gibbons
 ************************************************************************/
package job_tracker.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/************************************************************************
 * Class: Company
 *
 * Purpose:
 *      To handle and maintain company name and address information.
 *
 * Manager functions:
 *      - Company()
 *      - Company(int id, String name)
 *      - Company(int id, String name, String address)
 *
 * Methods:
 *      - int getId()
 *      - void setId(int id)
 *      - String getName()
 *      - void setName(String name)
 *      - String getAddress()
 *      - void setAddress(String address)
 *************************************************************************/
public class Company {
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
