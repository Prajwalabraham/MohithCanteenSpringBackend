package com.example.Canteen.Models;
import java.util.Date;

public class Menu {
    private long id;
    private String name;
    private String description;
    private Date createdAt;


    public Menu() {
        // Empty constructor needed for JSON deserialization
    }

    // Getter methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setter methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setCreatedAt(Date date){
        this.createdAt = date;
    }

    public void add(Menu menu) {
    }
}
